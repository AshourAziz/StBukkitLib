package com.stealthyone.mcb.stbukkitlib.help;

import com.stealthyone.mcb.stbukkitlib.storage.YamlFileManager;
import com.stealthyone.mcb.stbukkitlib.utils.MiscUtils;
import com.stealthyone.mcb.stbukkitlib.utils.QuickMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HelpManager {

    private JavaPlugin plugin;
    private String tag;
    private YamlFileManager helpFile;

    private final String msgInvalidPageDef = "&c&lNothing here.";
    private String msgInvalidPage;

    private final String msgNoDescriptionDef = "&cNo description set.";
    private String msgNoDescription;

    private final String msgSectionInfoDef = "&8Section: {SECTION} - {SECDESC}";
    private String msgSectionInfo;

    private final String msgUnknownSectionDef = "&cUnknown help section: &4{SECTION}&c.";
    private String msgUnknownSection;

    private HelpSection defaultSection;
    private HelpSection homeSection;

    public HelpManager(JavaPlugin plugin) {
        this(plugin, plugin.getName(), new YamlFileManager(plugin.getDataFolder() + File.separator + "help.yml"));
    }

    public HelpManager(JavaPlugin plugin, String tag, YamlFileManager file) {
        this.plugin = plugin;
        this.tag = tag;
        this.helpFile = file;
        defaultSection = new DefaultHelpSection();
    }

    public void reload() {
        if (helpFile.isEmpty()) {
            plugin.saveResource(helpFile.getFile().getName(), true);
        }
        helpFile.reloadConfig();
        FileConfiguration config = helpFile.getConfig();

        msgInvalidPage = config.getString("managerMessages.invalidPage", msgInvalidPageDef);
        msgNoDescription = config.getString("managerMessages.noDescription", msgNoDescriptionDef);
        msgSectionInfo = config.getString("managerMessages.sectionInfo", msgSectionInfoDef);
        msgUnknownSection = config.getString("managerMessages.unknownSection", msgUnknownSectionDef);

        homeSection = new HelpSection(this, defaultSection, config);
    }

    public void handleHelpCommand(String base, CommandSender sender, String label, String command, String[] args) {
        handleHelpCommand(base, sender, label, command, args, null);
    }

    public void handleHelpCommand(String base, CommandSender sender, String label, String command, String[] args, Map<String, String> replacements) {
        int startIndex = 0;

        if (command != null) {
            startIndex = command.split(" ").length;
        }

        helpCommand(startIndex, base, sender, label, command, args, replacements != null ? replacements : new HashMap<String, String>());
    }

    private void helpCommand(int curIndex, String base, CommandSender sender, String label, String command, String[] args, Map<String, String> replacements) {
        int page = 1;

        try {
            page = Integer.parseInt(args[curIndex]);
        } catch (NumberFormatException ex) {
            if (curIndex < args.length) {
                helpCommand(curIndex + 1, base, sender, label, command == null ? base : command + (base == null ? "" : (" " + base)), args, replacements);
                return;
            }
        } catch (IndexOutOfBoundsException ex) { }

        HelpSection helpSection;
        if (base == null) {
            helpSection = homeSection;
        } else {
            String[] baseSplit = base.split("\\.");
            HelpSection secIteration = homeSection;
            for (String string : baseSplit) {
                try {
                    secIteration = secIteration.getChild(string);
                } catch (NullPointerException ex) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msgUnknownSection.replace("{SECTION}", base == null ? "(none)" : base)));
                    return;
                }
            }
            helpSection = secIteration;
        }

        String perm = helpSection.getOptions().getPermission();
        if (perm != null && !perm.equals("")) {
            if (!sender.hasPermission(perm)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', helpSection.getOptions().getPermMessage()));
                return;
            }
        }

        List<String> messages = helpSection.getMessages();

        int pageCount = MiscUtils.getPageCount(messages.size(), helpSection.getItemsPerPage());

        Map<String, String> titleReplacements = new QuickMap<String, String>()
            .put("{TOPIC}", helpSection.getName() == null || helpSection.getName().equals("") ? tag : helpSection.getName())
            .put("{PATH}", helpSection.getPath() == null || helpSection.getPath().equals("") ? tag : helpSection.getPath().replace("\\.", "/"))
            .put("{PLUGIN}", tag)
            .put("{PAGE}", Integer.toString(page))
            .put("{MAXPAGES}", Integer.toString(pageCount))
            .put("{LABEL}", label)
            .put("{COMMAND}", command == null ? "" : " " + command + " ")
            .build();

        String header = helpSection.getHeader();
        String footer = helpSection.getFooter();
        String title = helpSection.getTitle();

        for (Entry<String, String> replacement : titleReplacements.entrySet()) {
            String key = replacement.getKey();
            String value = replacement.getValue();
            if (header != null && !header.equals("") && header.contains(key)) {
                header = header.replace(key, value);
            }
            if (footer != null && !footer.equals("") && footer.contains(key)) {
                footer = footer.replace(key, value);
            }
            if (title != null && !title.equals("") && title.contains(key)) {
                title = title.replace(key, value);
            }
        }

        List<String> formattedMessages = new ArrayList<>();
        if (header != null && !header.equals("")) {
            formattedMessages.add(ChatColor.translateAlternateColorCodes('&', header));
        }
        if (title != null && !title.equals("")) {
            formattedMessages.add(ChatColor.translateAlternateColorCodes('&', title));
        }
        for (int i = 0; i < helpSection.getItemsPerPage(); i++) {
            int index = i + ((page - 1) * helpSection.getItemsPerPage());
            try {
                String curMessage = messages.get(index);
                for (Entry<String, String> replacement : replacements.entrySet()) {
                    String key = replacement.getKey();
                    String value = replacement.getValue();
                    if (curMessage.contains(key)) {
                        curMessage = curMessage.replace(key, value);
                    }
                }
                formattedMessages.add(ChatColor.translateAlternateColorCodes('&', curMessage));
            } catch (IndexOutOfBoundsException ex) {
                if (i == 0) {
                    formattedMessages.add(ChatColor.translateAlternateColorCodes('&', msgInvalidPage));
                }
                break;
            }
        }
        if (footer != null && !footer.equals("")) {
            formattedMessages.add(ChatColor.translateAlternateColorCodes('&', footer));
        }

        if (!msgSectionInfo.equals("")) {
            List<String> subSections = new ArrayList<>();
            for (HelpSection child : helpSection.getChildren()) {
                String description = child.getOptions().getDescription();
                if (description == null || description.equals("")) {
                    description = msgNoDescription;
                }
                subSections.add(msgSectionInfo.replace("{SECTION}", child.getName()).replace("{SECDESC}", description));
            }

            for (String string : subSections) {
                formattedMessages.add(ChatColor.translateAlternateColorCodes('&', string));
            }
        }

        sender.sendMessage(formattedMessages.toArray(new String[formattedMessages.size()]));
    }

}