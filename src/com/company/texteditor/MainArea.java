package com.company.texteditor;

import com.company.texteditor.chainofresponsibility.Handler;
import com.company.texteditor.chainofresponsibility.SavedHandler;
import com.company.texteditor.chainofresponsibility.UnsavedHandler;
import com.company.texteditor.dbManager.*;
import com.company.texteditor.decorator.*;
import com.company.texteditor.factory.*;
import com.company.texteditor.iterator.IteratorMain;
import com.company.texteditor.login.Login;
import com.company.texteditor.memento.Caretaker;
import com.company.texteditor.memento.Originator;
import com.company.texteditor.observer.LanguageSubject;
import com.company.texteditor.observer.Observer;
import com.company.texteditor.state.Context;
import com.company.texteditor.strategy_language.Chinese;
import com.company.texteditor.strategy_language.English;
import com.company.texteditor.strategy_language.Language;
import com.company.texteditor.strategy_theme.*;
import com.company.texteditor.text_storage.TextStorage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;

public class MainArea extends JPanel implements Observer {
    private User user;
    private LanguageSubject languageSubject;
    private TextStorage textStorage;

    private WindowAdapter windowAdapter;

    private MainFrame mainFrame;
    private Theme theme;

    private JPanel topArea;
    private JPanel centerArea;
    private JPanel bottomArea;

    private JButton logoutBtn;

    private JPopupMenu popUpMenu;

    // top area
    private Glyph textGlyph = new TextGlyph();
    private JLabel fontLabel;
    private JSpinner fontSizeSpinner;
    private JButton colorButton;
    private JComboBox fontBox;
    private Color color;

    // center area
    private JTextPane textPane;

    // bottom area
    // State
    private JLabel stateBar;
    private Context context;

    // Memento
    private Caretaker caretaker;
    private Originator originator;
    private int curStateIndex = 0;
    private boolean isUndoKey;
    private boolean isRedoKey;
    private boolean isBoldKey;
    private boolean isItalicKey;
    private boolean isUnderlineKey;
    private boolean isCommonKey;
    // Memento State
    private String text;
    private List<AttributeSet> attributeSetList;
    private int textSize;
    private Color textColor;
    private String textFamily;

    // menu --------------------------------------------------------------------------------------------------------------------
    private JMenuBar menuBar;
    // File
    private JMenu fileMenu;
    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem saveToItem;
    // Edit
    private JMenu editMenu;
    private JMenuItem cutItem;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;
    private JMenuItem undoItem;
    private JMenuItem redoItem;
    private JMenuItem findAndReplaceItem;
    private JMenuItem selectAllItem;
    private JMenuItem insertImageItem;
    // Glyph
    private JMenu glyphMenu;
    private JMenuItem boldItem;
    private JMenuItem italicItem;
    private JMenuItem underlineItem;
    // Theme
    private JMenu themeMenu;
    private JMenuItem whiteThemeItem;
    private JMenuItem darkThemeItem;
    private JMenuItem highContrastWhiteThemeItem;
    private JMenuItem highContrastDarkThemeItem;
    ButtonGroup themeGroup = new ButtonGroup();
    // Language
    private JMenu languageMenu;
    private JMenuItem englishItem;
    private JMenuItem chineseItem;
    private ButtonGroup languageGroup = new ButtonGroup();
    // Help
    private JMenu helpMenu;
    private JMenuItem aboutItem;
    // menu end ----------------------------------------------------------------------------------------------------------------

    public MainArea(LanguageSubject languageSubject, MainFrame mainFrame, Theme theme, User user) {
        this.languageSubject = languageSubject;
        languageSubject.registerObserver(this);
        this.theme = theme;
        this.mainFrame = mainFrame;
        this.user = user;

        // set default language
        switch (user.getCurrent_language()) {
            case "English":
                Language language = new Language();
                language.setLanguageStrategy(new English());
                this.languageSubject.changeLanguage(language);
                break;
            case "Chinese":
                Language language1 = new Language();
                language1.setLanguageStrategy(new Chinese());
                this.languageSubject.changeLanguage(language1);
                break;
        }

        // set default theme
        switch (user.getCurrent_theme()) {
            case "White":
                this.theme.setWindowTheme(new WhiteWindow());
                break;
            case "Dark":
                this.theme.setWindowTheme(new DarkWindow());
                break;
            case "HighContrastWhite":
                this.theme.setWindowTheme(new HighContrastWhiteWindow());
                break;
            case "HighContrastDark":
                this.theme.setWindowTheme(new HighContrastDarkWindow());
                break;
        }
        theme.performWindow();

        textStorage = this.languageSubject.getTextStorage();

        this.mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        windowAdapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Handler handler = new SavedHandler(new UnsavedHandler(null, languageSubject));
                System.out.println(context.getState());
                handler.handleExit(context.getState());
            }
        };

        this.mainFrame.addWindowListener(windowAdapter);

        // Memento
        originator = new Originator();
        caretaker = new Caretaker();

        // State
        context = new Context(languageSubject);

        topArea = new JPanel(new BorderLayout());
        centerArea = new JPanel();
        bottomArea = new JPanel();

        setLayout(new BorderLayout());
        menu();
        mainFrame.setJMenuBar(menuBar);
        mainBody();
    }

    public void mainBody() {
        textPane = new JTextPane();

        // Key Listener
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                context.Handle(true);
            }
        });

        // Undo Listener
        textPane.getDocument().addUndoableEditListener(
                e -> {
                    textPane.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            isUndoKey = e.getKeyCode() == 90;
                            isUndoKey = e.isControlDown();
                            isRedoKey = e.getKeyCode() == 89;
                            isRedoKey = e.isControlDown();
                        }
                    });
                    if (!isUndoKey && !isRedoKey && !isBoldKey && !isItalicKey && !isUnderlineKey && !isCommonKey) {
                        curStateIndex++;
                        storeMemento();
                    }
                }
        );

        System.out.println("theme: " + theme.getWindowColor().getClass().getName());
        switch (theme.getWindowColor().getClass().getName()) {
            case "com.company.texteditor.strategy_theme.WhiteWindow":
                System.out.println("White");
                whiteThemeItem.setSelected(true);
                break;
            case "com.company.texteditor.strategy_theme.DarkWindow":
                System.out.println("Dark");
                darkThemeItem.setSelected(true);
                break;
            case "com.company.texteditor.strategy_theme.HighContrastWhiteWindow":
                System.out.println("HighWhite");
                highContrastWhiteThemeItem.setSelected(true);
                break;
            case "com.company.texteditor.strategy_theme.HighContrastDarkWindow":
                System.out.println("HighDark");
                highContrastDarkThemeItem.setSelected(true);
                break;
        }

        System.out.println("lan: " + languageSubject.getTextStorage().getText("lan"));
        switch (languageSubject.getTextStorage().getText("lan")) {
            case "en":
                englishItem.setSelected(true);
                break;
            case "ch":
                chineseItem.setSelected(true);
                break;
        }

        // top area --------------------------------------------------------------------------------------------------------
        // change font size
        fontLabel = new JLabel(textStorage.getText("Font:"));
        fontSizeSpinner = new JSpinner();
        colorButton = new JButton(textStorage.getText("Text Color"));
        colorButton.addActionListener(this::actionPerformed);
        fontSizeSpinner.setPreferredSize(new Dimension(60,25));
        fontSizeSpinner.setValue(20);
        textSize = 20;
        textPane.setFont(new Font(textPane.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // memento state: textSize
                textSize = (int)fontSizeSpinner.getValue();
                curStateIndex++;
                storeMemento();
                textPane.setFont(new Font(textPane.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
            }
        });

        // create font family
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox(fonts); // choose and change font
        fontBox.setSelectedItem("Arial"); // default font
        fontBox.addActionListener(this::actionPerformed);
        textFamily = "Arial";

        JPanel leftArea = new JPanel();
        JPanel rightArea = new JPanel();

        logoutBtn = new JButton(textStorage.getText("Logout"));
        logoutBtn.addActionListener(this::actionPerformed);

        leftArea.add(fontLabel);
        leftArea.add(fontSizeSpinner);
        leftArea.add(colorButton);
        leftArea.add(fontBox);
        rightArea.add(logoutBtn);
        topArea.add(leftArea, BorderLayout.WEST);
        topArea.add(rightArea, BorderLayout.EAST);

        // center area -----------------------------------------------------------------------------------------------------
        centerArea.setLayout(new BorderLayout());
        TitledBorder tb = BorderFactory.createTitledBorder(textStorage.getText("Text Edit Area"));
        tb.setTitlePosition(TitledBorder.ABOVE_TOP);
        centerArea.setBorder(tb);

        // add textPane
        JScrollPane scrollPane = new JScrollPane(textPane);
        centerArea.add(scrollPane);


        // bottom area -----------------------------------------------------------------------------------------------------
        stateBar = context.stateBar;
        stateBar.setHorizontalAlignment(SwingConstants.LEFT);
        stateBar.setBorder(BorderFactory.createEtchedBorder());
        bottomArea.add(stateBar);

        // add component -----------------------------------------------------------------------------------------------------
        add(topArea, BorderLayout.NORTH);
        add(centerArea, BorderLayout.CENTER);
        add(bottomArea, BorderLayout.SOUTH);

//        //add mouse event
//        textPane.addMouseListener(
//                new MouseAdapter() {
//                    public void mouseReleased(MouseEvent e) {
//                        if(e.getButton() == MouseEvent.BUTTON3)
//                            popUpMenu.show(editMenu, e.getX(), e.getY());
//                            popUpMenu.setVisible(true);
//                    }
//                    public void mouseClicked(MouseEvent e) {
//                        if(e.getButton() == MouseEvent.BUTTON1)
//                            popUpMenu.setVisible(false);
//                    }
//                }
//        );

        // store memento 0
        storeMemento();
    }

    public void menu() {
        // menu bar
        menuBar = new JMenuBar();
        // file --------------------------------------------------------------------------------------------------------
        fileMenu = new JMenu(textStorage.getText("File"));
        newItem = new JMenuItem(textStorage.getText("New"));
        openItem = new JMenuItem(textStorage.getText("Open"));
        saveItem = new JMenuItem(textStorage.getText("Save"));
        saveToItem = new JMenuItem(textStorage.getText("SaveTo"));
        newItem.addActionListener(this::actionPerformed);
        openItem.addActionListener(this::actionPerformed);
        saveItem.addActionListener(this::actionPerformed);
        saveToItem.addActionListener(this::actionPerformed);
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveToItem);
        // edit --------------------------------------------------------------------------------------------------------
        editMenu = new JMenu(textStorage.getText("Edit"));
        cutItem = new JMenuItem(textStorage.getText("Cut"));
        copyItem = new JMenuItem(textStorage.getText("Copy"));
        pasteItem = new JMenuItem(textStorage.getText("Paste"));
        undoItem = new JMenuItem(textStorage.getText("Undo"));
        redoItem = new JMenuItem(textStorage.getText("Redo"));
        findAndReplaceItem = new JMenuItem(textStorage.getText("Find and Replace"));
        selectAllItem = new JMenuItem(textStorage.getText("Select All"));
        insertImageItem = new JMenuItem(textStorage.getText("Insert Image"));
        copyItem.addActionListener(this::actionPerformed);
        pasteItem.addActionListener(this::actionPerformed);
        cutItem.addActionListener(this::actionPerformed);
        undoItem.addActionListener(this::actionPerformed);
        redoItem.addActionListener(this::actionPerformed);
        findAndReplaceItem.addActionListener(this::actionPerformed);
        selectAllItem.addActionListener(this::actionPerformed);
        insertImageItem.addActionListener(this::actionPerformed);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(cutItem);
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.add(findAndReplaceItem);
        editMenu.add(selectAllItem);
        editMenu.add(insertImageItem);
        // glyph --------------------------------------------------------------------------------------------------------
        glyphMenu = new JMenu(textStorage.getText("Glyph"));
        boldItem = new JMenuItem(textStorage.getText("Bold"));
        italicItem = new JMenuItem(textStorage.getText("Italic"));
        underlineItem = new JMenuItem(textStorage.getText("Underline"));
        boldItem.addActionListener(this::actionPerformed);
        italicItem.addActionListener(this::actionPerformed);
        underlineItem.addActionListener(this::actionPerformed);
        glyphMenu.add(boldItem);
        glyphMenu.add(italicItem);
        glyphMenu.add(underlineItem);
        // theme --------------------------------------------------------------------------------------------------------
        themeMenu = new JMenu(textStorage.getText("Theme"));
        whiteThemeItem = new JRadioButtonMenuItem(textStorage.getText("White Theme"));
        darkThemeItem = new JRadioButtonMenuItem(textStorage.getText("Dark Theme"));
        highContrastWhiteThemeItem = new JRadioButtonMenuItem(textStorage.getText("High Contrast White"));
        highContrastDarkThemeItem = new JRadioButtonMenuItem(textStorage.getText("High Contrast Dark"));
        whiteThemeItem.addActionListener(this::actionPerformed);
        darkThemeItem.addActionListener(this::actionPerformed);
        highContrastWhiteThemeItem.addActionListener(this::actionPerformed);
        highContrastDarkThemeItem.addActionListener(this::actionPerformed);
        themeGroup.add(whiteThemeItem);
        themeGroup.add(darkThemeItem);
        themeGroup.add(highContrastWhiteThemeItem);
        themeGroup.add(highContrastDarkThemeItem);
        themeMenu.add(whiteThemeItem);
        themeMenu.add(darkThemeItem);
        themeMenu.add(highContrastWhiteThemeItem);
        themeMenu.add(highContrastDarkThemeItem);
        // language --------------------------------------------------------------------------------------------------------
        languageMenu = new JMenu(textStorage.getText("Language"));
        englishItem = new JRadioButtonMenuItem(textStorage.getText("English"));
        chineseItem = new JRadioButtonMenuItem(textStorage.getText("Chinese"));
        englishItem.addActionListener(this::actionPerformed);
        chineseItem.addActionListener(this::actionPerformed);
        languageGroup.add(englishItem);
        languageGroup.add(chineseItem);
        languageMenu.add(englishItem);
        languageMenu.add(chineseItem);
        // about --------------------------------------------------------------------------------------------------------
        helpMenu = new JMenu(textStorage.getText("Help"));
        aboutItem = new JMenuItem(textStorage.getText("About"));
        aboutItem.addActionListener(this::actionPerformed);
        helpMenu.add(aboutItem);

        // Key Stroke --------------------------------------------------------------------------------------------------------
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,  InputEvent.CTRL_DOWN_MASK));
        saveToItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,  InputEvent.CTRL_DOWN_MASK+SHIFT_DOWN_MASK));
        cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        findAndReplaceItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(glyphMenu);
        menuBar.add(themeMenu);
        menuBar.add(languageMenu);
        menuBar.add(helpMenu);
    }

    private void actionPerformed(ActionEvent e) {
        // file --------------------------------------------------------------------------------------------------------------
        if (e.getSource() == newItem) {
            MainWindow mainWindow = new MainWindow(languageSubject, user);
        }

        if (e.getSource() == openItem) {
            if (e.getSource() == openItem) {  //open the file

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));
                // get file path and choose file

                context.initial(); // set stateBar
                int response = fileChooser.showOpenDialog(null);

                if (response == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    Scanner filein = null;
                    // set the filename on the title
                    mainFrame.setTitle(fileChooser.getSelectedFile().toString());
                    try {
                        filein = new Scanner(file);
                        if (file.isFile()) {
                            while (filein.hasNextLine()) {
                                String line = filein.nextLine() + "\n";
                                textPane.setText(line);
                                // add lines to textPane
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        filein.close();
                    }
                }
            }
        }

        if (e.getSource() == saveItem) {  // save the file
            context.Handle(false); //set stateBar

            PrintWriter fileout = null;
            File file = new File(mainFrame.getTitle());
            if(file.exists()){
                try {
                    fileout = new PrintWriter(file);
                    fileout.println(textPane.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    fileout.close();
                }
            }else {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("."));

                int response = fileChooser.showSaveDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    fileout = null;
                    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                    // set the filename on the title
                    mainFrame.setTitle(file.toString());

                    try {
                        fileout = new PrintWriter(file);
                        fileout.println(textPane.getText());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        fileout.close();
                    }
                    //save the document.
                }
            }
        }

        if (e.getSource() == saveToItem) {  // save the file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            context.Handle(false); //set stateBar
            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file;
                PrintWriter fileout = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                // set the filename on the title
                mainFrame.setTitle(file.toString());

                try {
                    fileout = new PrintWriter(file);
                    fileout.println(textPane.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } finally {
                    fileout.close();
                }
                //save the document.
            }
        }

        // edit --------------------------------------------------------------------------------------------------------------
        if (e.getSource() == copyItem){
            Select_Function select = new Select_Copy();
            Functions fun = select.fun(textPane);
            fun.getType();
        }
        if (e.getSource() == pasteItem){
            context.Handle(true);   //set the state
            Select_Function select = new Select_Paste();
            Functions fun = select.fun(textPane);
            fun.getType();
        }
        if (e.getSource() == cutItem){
            context.Handle(true);   //set the state
            Select_Function select = new Select_Cut();
            Functions fun = select.fun(textPane);
            fun.getType();
        }
        if (e.getSource() == selectAllItem){
            Select_Function select = new Select_SelectAll();
            Functions fun = select.fun(textPane);
            fun.getType();
        }

        if (e.getSource() == findAndReplaceItem){
            IteratorMain.setJP(mainFrame,textPane,languageSubject);
            context.Handle(true);   //set the state
        }

        if (e.getSource() == undoItem) {
            context.Handle(true);   //set the state
            isUndoKey = true;

            if (curStateIndex <= 1) {
                if (curStateIndex == 1) {
                    curStateIndex--;
                    System.out.println("Undo-curStateIndex: " + curStateIndex);
                    System.out.println("Undo-mementoListSize: " + caretaker.getMementoListSize());
                    textPane.setText("");
                }
            } else {
                curStateIndex--;
                System.out.println("Undo-curStateIndex: " + curStateIndex);
                System.out.println("Undo-mementoListSize: " + caretaker.getMementoListSize());
                resetTextPane();
                isUndoKey = false;
            }
        }

        if (e.getSource() == redoItem) {
            context.Handle(true);   //set the state
            isRedoKey = true;

            if (curStateIndex <= caretaker.getMementoListSize()-2) {
                curStateIndex++;
                System.out.println("Redo-curStateIndex: " + curStateIndex);
                System.out.println("Redo-mementoListSize: " + caretaker.getMementoListSize());
                resetTextPane();
                isRedoKey = false;
            }
        }

        if (e.getSource() == insertImageItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {

                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                if (file.isFile()) {
                    textPane.insertIcon(new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath()));
                    context.Handle(true);   //set the state
                }
            }
        }

        // glyph --------------------------------------------------------------------------------------------------------------
        if (e.getSource() == boldItem){
            context.Handle(true);   //set the state
            isBoldKey = true;
            textGlyph = new Bold(textGlyph, textPane);
            curStateIndex++;
            storeMemento();
            isBoldKey = false;
        }

        if (e.getSource() == italicItem){
            context.Handle(true);   //set the state
            isItalicKey = true;
            textGlyph = new Italic(textGlyph, textPane);
            curStateIndex++;
            storeMemento();
            isItalicKey = false;
        }

        if (e.getSource() == underlineItem){
            context.Handle(true);   //set the state
            isUnderlineKey = true;
            textGlyph = new Underline(textGlyph, textPane);
            curStateIndex++;
            storeMemento();
            isItalicKey = false;
        }

        // theme --------------------------------------------------------------------------------------------------------------
        if (e.getSource() == whiteThemeItem){
            theme.setWindowTheme(new WhiteWindow());
            theme.performWindow();

            // change theme data
            DBTemplate changeTheme = new ChangeUserTheme(this.user.getId(), "White");
            changeTheme.execute();
        }

        if (e.getSource() == darkThemeItem){
            theme.setWindowTheme(new DarkWindow());
            theme.performWindow();

            // change theme data
            DBTemplate changeTheme = new ChangeUserTheme(this.user.getId(), "Dark");
            changeTheme.execute();
        }

        if (e.getSource() == highContrastWhiteThemeItem) {
            theme.setWindowTheme(new HighContrastWhiteWindow());
            theme.performWindow();

            // change theme data
            DBTemplate changeTheme = new ChangeUserTheme(this.user.getId(), "HighContrastWhite");
            changeTheme.execute();
        }

        if (e.getSource() == highContrastDarkThemeItem) {
            theme.setWindowTheme(new HighContrastDarkWindow());
            theme.performWindow();

            // change theme data
            DBTemplate changeTheme = new ChangeUserTheme(this.user.getId(), "HighContrastDark");
            changeTheme.execute();
        }

        // language --------------------------------------------------------------------------------------------------------------
        if(e.getSource() == englishItem) {
            if (textStorage.getText("English") == "English") {
                System.out.println("nothing to do");
            } else {
                Language language = new Language();
                language.setLanguageStrategy(new English());
                languageSubject.changeLanguage(language);
                languageSubject.notifyObserver();

                // change theme data
                DBTemplate changeLan = new ChangeUserLan(this.user.getId(), "English");
                changeLan.execute();
            }
        }

        if(e.getSource() == chineseItem){
            if (textStorage.getText("English") == "英文") {
                System.out.println("nothing to do");
            } else {
                Language language = new Language();
                language.setLanguageStrategy(new Chinese());
                languageSubject.changeLanguage(language);
                languageSubject.notifyObserver();

                // change theme data
                DBTemplate changeLan = new ChangeUserLan(this.user.getId(), "Chinese");
                changeLan.execute();
            }
        }

        // about --------------------------------------------------------------------------------------------------------------
        if (e.getSource() == aboutItem) {
            Object[] checkButton = {textStorage.getText("OK")};
            Object defaultCheckButton = checkButton[0];
            ImageIcon icon = new ImageIcon("src/llama.png");
            Image image = icon.getImage(); // transform it
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(newimg);// transform it back
            JOptionPane.showOptionDialog(mainFrame,
                    textStorage.getText("This is a Text Editor."),
                    textStorage.getText("Message"),
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.INFORMATION_MESSAGE,
                    icon,
                    checkButton,
                    defaultCheckButton);
        }

        // font --------------------------------------------------------------------------------------------------------------
        if (e.getSource() == colorButton) {
            context.Handle(true);   //set the state
            // memento state: textColor
            color = JColorChooser.showDialog(null, textStorage.getText("Choose a color"), Color.BLACK);
            textColor = color;
            curStateIndex++;
            storeMemento();
            textPane.setForeground(color);
        }

        if (e.getSource() == fontBox) {
            context.Handle(true);   //set the state
            // change text font
            textPane.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textPane.getFont().getSize()));
            // memento state: textFamily
            textFamily = fontBox.getSelectedItem().toString();
            curStateIndex++;
            storeMemento();
        }

        if (e.getSource() == logoutBtn) {
            // remove menuBar
            menuBar.remove(fileMenu);
            menuBar.remove(editMenu);
            menuBar.remove(glyphMenu);
            menuBar.remove(themeMenu);
            menuBar.remove(languageMenu);
            menuBar.remove(helpMenu);

            this.mainFrame.removeWindowListener(windowAdapter);

            Panel panel = (Panel) getParent();
            panel.add(new Login(languageSubject, mainFrame, theme), "login");
            panel.cl.show(panel, "login");
        }
    }

    private void storeMemento() {
        text = textPane.getText();
        attributeSetList = new ArrayList<>();
        System.out.println("storeMemento-textLen: " + text.length());
        for(int i = 0; i < text.length(); i++) {
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            AttributeSet set = textPane.getStyledDocument().getCharacterElement(i).getAttributes();
            // bold
            if (StyleConstants.isBold(set)) {
                StyleConstants.setBold(simpleAttributeSet, true);
            } else {
                StyleConstants.setBold(simpleAttributeSet, false);
            }
            // italic
            if (StyleConstants.isItalic(set)) {
                StyleConstants.setItalic(simpleAttributeSet, true);
            } else {
                StyleConstants.setItalic(simpleAttributeSet, false);
            }
            // underline
            if (StyleConstants.isUnderline(set)) {
                StyleConstants.setUnderline(simpleAttributeSet, true);
            } else {
                StyleConstants.setUnderline(simpleAttributeSet, false);
            }
            attributeSetList.add(simpleAttributeSet);
        }

        originator.setText(text);
        originator.setAttributeSetList(attributeSetList);
        originator.setTextColor(textColor);
        originator.setTextSize(textSize);
        originator.setTextFamily(textFamily);
        caretaker.addMemento(curStateIndex, originator.storeInMemento());
    }

    private void resetTextPane() {
        originator.restoreFromMemento(caretaker.getMemento(curStateIndex));
        textPane.setText(originator.getText());

        for(int i = 0; i < originator.getText().length(); i++) {
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            StyledDocument doc = (StyledDocument) textPane.getDocument();
            doc.setCharacterAttributes(i, 1, originator.getAttributeSetList().get(i), true);
        }

        textPane.setForeground(originator.getTextColor());
        textPane.setFont(new Font(originator.getTextFamily(),Font.PLAIN,originator.getTextSize()));
    }

    @Override
    public void update() {
        textStorage = languageSubject.getTextStorage();
        fontLabel.setText(textStorage.getText("Font Size:"));
        colorButton.setText(textStorage.getText("Text Color"));
        fileMenu.setText(textStorage.getText("File"));
        editMenu.setText(textStorage.getText("Edit"));
        glyphMenu.setText(textStorage.getText("Glyph"));
        themeMenu.setText(textStorage.getText("Theme"));
        languageMenu.setText(textStorage.getText("Language"));
        helpMenu.setText(textStorage.getText("Help"));
        newItem.setText(textStorage.getText("New"));
        openItem.setText(textStorage.getText("Open"));
        saveItem.setText(textStorage.getText("Save"));
        saveToItem.setText(textStorage.getText("SaveTo"));
        cutItem.setText(textStorage.getText("Cut"));
        copyItem.setText(textStorage.getText("Copy"));
        pasteItem.setText(textStorage.getText("Paste"));
        undoItem.setText(textStorage.getText("Undo"));
        redoItem.setText(textStorage.getText("Redo"));
        findAndReplaceItem.setText(textStorage.getText("Find and Replace"));
        selectAllItem.setText(textStorage.getText("Select All"));
        insertImageItem.setText(textStorage.getText("Insert Image"));
        boldItem.setText(textStorage.getText("Bold"));
        italicItem.setText(textStorage.getText("Italic"));
        underlineItem.setText(textStorage.getText("Underline"));
        whiteThemeItem.setText(textStorage.getText("White Theme"));
        darkThemeItem.setText(textStorage.getText("Dark Theme"));
        highContrastWhiteThemeItem.setText(textStorage.getText("High Contrast White"));
        highContrastDarkThemeItem.setText(textStorage.getText("High Contrast Dark"));
        aboutItem.setText(textStorage.getText("About"));
        englishItem.setText(textStorage.getText("English"));
        chineseItem.setText(textStorage.getText("Chinese"));
        logoutBtn.setText(textStorage.getText("Logout"));

        // center area border
        TitledBorder tb = BorderFactory.createTitledBorder(textStorage.getText("Text Edit Area"));
        tb.setTitlePosition(TitledBorder.ABOVE_TOP);
        centerArea.setBorder(tb);
    }
}
