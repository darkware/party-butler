package partybutler.ui;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import partybutler.Constants;
import partybutler.player.CurrentSongInformation;
import partybutler.player.files.Library;
import partybutler.player.files.interfaces.MediaFile;
import partybutler.player.files.queue.UserQueue;
import partybutler.player.media.PartyPlayer;
import partybutler.song.info.Album;
import partybutler.song.info.Artist;
import partybutler.ui.customcomponents.JImagePanel;
import partybutler.ui.customcomponents.RoundButton;
import partybutler.ui.customcomponents.coverflow.CDShelf;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Administrator on 25.03.2014.
 */
public class MainFrame extends JFrame {
    private JPanel searchButtonField;
    private JPanel rootPanel;
    private JPanel buttonsPanel;
    private JPanel settingsPanel;
    private JPanel controlPanel;
    private JButton searchButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton playButton;
    private JButton addMenuButton;
    private JButton settingsMenuButton;
    private JPanel volumeSliderPanel;
    private JSlider volumeSlider2;
    private JLabel volumeText;
    private JLabel artistLabel;
    private JLabel albumLabel;
    private JLabel titleLabel;
    private JButton artistMoreButton;
    private JButton albumMoreButton;
    private JButton titleMoreButton;
    private JPanel listContainerPanel;
    private JSlider timeSlider;
    private JPanel timeSliderPanel;
    private JLabel timeText;
    private JPanel timePanel;
    private JPanel labelPanel;
    private JPanel queuePanel;
    private JPanel controlButtonsPanel;
    private JPanel sliderPanel;
    private JPanel volumeTextPanel;
    private JPanel timeTextPanel;
    private JPanel artistLabelPanel;
    private JPanel albumLabelPanel;
    private JPanel titleLabelPanel;
    private JPanel listContainerInnerPanel;

    private CDShelf coverFlow;

    private JList entryList;
    private JScrollPane scrollPane;
    private JButton flowButton;

    private Artist selectedArtist;
    private Album selectedAlbum;
    private MediaFile selectedMediaFile;

    private PartyPlayer partyPlayer;

    private int currentListType = 0;

    public MainFrame() {
        super(Constants.APPLICATION_NAME);
        setContentPane(rootPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

        partyPlayer = PartyPlayer.getInstance();
        partyPlayer.setBasicPlayerListener(uiPlayerListener);

        initializeUI();

        artistMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showArtistList();
            }
        });

        albumMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAlbumList(null);
            }
        });

        titleMoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTitleList(null);
            }
        });

        flowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCoverFlow();
            }
        });


        entryList.addMouseListener(listMouseAdapter);
        entryList.addListSelectionListener(listSelectionListener);

        scrollPane.getVerticalScrollBar().setBackground(new Color(94, 94, 94, 255));
        scrollPane.setViewportBorder(null);
    }

    private void initializeUI() {
        initializeSearchButton();
        initializeSliders();

        changeBackGround(rootPanel.getComponents());
        updateLabels();
    }

    private void changeBackGround(Component[] components) {
        for (Component com : components) {
            if (com instanceof JComponent) {
                ((JComponent) com).setOpaque(false);
            }
            if (com instanceof Container) {
                if (!(com instanceof JSlider)) {
                    com.setBackground(new Color(94, 94, 94, 0));
                    if (com instanceof JPanel) {
                        ((JPanel) com).setDoubleBuffered(true);
                        ((JPanel) com).setOpaque(false);
                    }
                    changeBackGround(((Container) com).getComponents());
                }
            }
        }
    }

    private void initializeSliders() {
        /*
        START SLIDER INITIALIZATION
         */
        UIDefaults sliderDefaults = new UIDefaults();

        sliderDefaults.put("Slider.thumbWidth", 20);
        sliderDefaults.put("Slider.thumbHeight", 20);
        sliderDefaults.put("Slider:SliderThumb.backgroundPainter", new com.sun.java.swing.Painter<JComponent>() {

            @Override
            public void paint(Graphics2D g, Object object, int w, int h) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(2f));
                g.setColor(new Color(250, 171, 0));
                g.fillOval(1, 1, w - 3, h - 3);
                g.setColor(new Color(251, 125, 7));
                g.drawOval(1, 1, w - 3, h - 3);
            }
        });
        sliderDefaults.put("Slider:SliderTrack.backgroundPainter", new com.sun.java.swing.Painter<JComponent>() {
            @Override
            public void paint(Graphics2D g, Object object, int w, int h) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(2f));
                g.setColor(new Color(94, 94, 94, 0));
                g.fillRoundRect(0, 6, w - 1, 8, 8, 8);
                g.setColor(new Color(251, 125, 7));
                g.drawRoundRect(0, 6, w - 1, 8, 8, 8);
            }
        });
        volumeSlider2.putClientProperty("Nimbus.Overrides", sliderDefaults);
        volumeSlider2.putClientProperty("Nimbus.Overrides.InheritDefaults", false);
        volumeSlider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                volumeText.setText(Constants.VOLUME_TEXT + volumeSlider2.getValue() + "%");
                partyPlayer.setVolume(volumeSlider2.getValue());
            }
        });

        timeSlider.putClientProperty("Nimbus.Overrides", sliderDefaults);
        timeSlider.putClientProperty("Nimbus.Overrides.InheritDefaults", false);

        /*
        END SLIDER INITIALIZATION
         */
    }

    private void initializeSearchButton() {
        //Search Button
        searchButton.setMargin(new Insets(0, 0, 0, 0));
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setFocusPainted(false);
        searchButton.setOpaque(false);
        searchButton.setBorder(null);
        searchButton.setPressedIcon(new ImageIcon("ui-resources\\buttons\\search_round_pressed.png"));
        searchButton.setIcon(new ImageIcon("ui-resources\\buttons\\search_round_normal.png"));
        searchButton.setRolloverIcon(new ImageIcon("ui-resources\\buttons\\search_round_hover.png"));
    }

    private void createUIComponents() {
        try {
            rootPanel = new JImagePanel(ImageIO.read(new File("ui-resources\\background.jpg")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        createControlButtons();
    }


    private void createControlButtons() {
        previousButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\back_round_normal.png"), "ui-resources\\buttons\\back_round_pressed.png", "ui-resources\\buttons\\back_round_hover.png");
        playButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\play_round_normal.png"), "ui-resources\\buttons\\play_round_pressed.png", "ui-resources\\buttons\\play_round_hover.png");
        nextButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\next_round_normal.png"), "ui-resources\\buttons\\next_round_pressed.png", "ui-resources\\buttons\\next_round_hover.png");
        addMenuButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\add_round_normal.png"), "ui-resources\\buttons\\add_round_pressed.png", "ui-resources\\buttons\\add_round_hover.png");
        settingsMenuButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\settings_round_normal.png"), "ui-resources\\buttons\\settings_round_pressed.png", "ui-resources\\buttons\\settings_round_hover.png");
        artistMoreButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\more_round_normal.png"), "ui-resources\\buttons\\more_round_pressed.png", "ui-resources\\buttons\\more_round_hover.png");
        albumMoreButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\more_round_normal.png"), "ui-resources\\buttons\\more_round_pressed.png", "ui-resources\\buttons\\more_round_hover.png");
        titleMoreButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\more_round_normal.png"), "ui-resources\\buttons\\more_round_pressed.png", "ui-resources\\buttons\\more_round_hover.png");
        flowButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\flow_round_normal.png"), "ui-resources\\buttons\\flow_round_pressed.png", "ui-resources\\buttons\\flow_round_hover.png");
    }

    public void setEntryList(ArrayList<Artist> artists) {
        entryList.setListData(new Vector(artists));
    }

    private void showCoverFlow() {
        if(currentListType != Constants.LISTTYPE_COVERFLOW) {
            if (selectedArtist != null) {
                coverFlow = new CDShelf(selectedArtist.getAlbums());
            } else {
                coverFlow = new CDShelf(Library.getInstance().getAllAlbums());
            }
            coverFlow.setMaximumSize(new Dimension(600, 200));
            coverFlow.setPreferredSize(new Dimension(600, 200));
            coverFlow.setMinimumSize(new Dimension(600, 200));

            coverFlow.addListSelectionListener(coverFlowSelectionListener);
            coverFlow.addActionListener(coverFlowActionListener);


            listContainerInnerPanel.remove(scrollPane);
            listContainerInnerPanel.setLayout(new BorderLayout());
            listContainerInnerPanel.add(coverFlow);
            coverFlow.revalidate();
            currentListType = Constants.LISTTYPE_COVERFLOW;
        }

    }

    private void hideCoverFlow() {
        listContainerInnerPanel.remove(coverFlow);
        listContainerInnerPanel.add(scrollPane, BorderLayout.CENTER);
        entryList.revalidate();
        listContainerInnerPanel.revalidate();
        coverFlow.revalidate();
    }

    private void showArtistList() {
        if (currentListType == Constants.LISTTYPE_COVERFLOW) {
            hideCoverFlow();
        }
        entryList.setListData(new Vector<Artist>(Library.getInstance().getArtists()));
        selectedArtist = null;
        selectedAlbum = null;
        selectedMediaFile = null;
        currentListType = Constants.LISTTYPE_ARTIST;
        updateLabels();
    }

    private void showAlbumList(Artist artist) {
        if (currentListType == Constants.LISTTYPE_COVERFLOW) {
            hideCoverFlow();
        }

        if(currentListType == Constants.LISTTYPE_ALBUM){
            selectedArtist = null;
        }

        if (artist != null) {
            entryList.setListData(new Vector<Album>(artist.getAlbums()));
            selectedArtist = artist;
        } else if (selectedArtist != null) {
            entryList.setListData(new Vector<Album>(selectedArtist.getAlbums()));
        } else {
            entryList.setListData(new Vector<Album>(Library.getInstance().getAllAlbums()));
        }
        selectedAlbum = null;
        selectedMediaFile = null;

        currentListType = Constants.LISTTYPE_ALBUM;

        updateLabels();
    }

    private void showTitleList(Album album) {
        if (currentListType == Constants.LISTTYPE_COVERFLOW) {
            hideCoverFlow();
        }
        if (album != null) {
            entryList.setListData(new Vector<MediaFile>(album.getMediaFiles()));
            selectedAlbum = album;
            selectedArtist = album.getArtist();
        } else {
            entryList.setListData(new Vector<MediaFile>(Library.getInstance().getAllMediaFiles()));
            selectedArtist = null;
            selectedAlbum = null;
        }
        selectedMediaFile = null;
        currentListType = Constants.LISTTYPE_TITLE;
        updateLabels();
    }

    private MouseAdapter listMouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent evt) {
            if (entryList.getSelectedValue() instanceof Artist) {
                if (evt.getClickCount() == 2) {
                    UserQueue.getInstance().enqueueAllFirst(((Artist) entryList.getSelectedValue()).getAllMediaFiles());
                } else if (evt.getClickCount() == 3) {   // Triple-click
                    UserQueue.getInstance().enqueueAllFirst(((Artist) entryList.getSelectedValue()).getAllMediaFiles());
                }
            } else if (entryList.getSelectedValue() instanceof Album) {
                if (evt.getClickCount() == 2) {
                    UserQueue.getInstance().enqueueAllFirst(((Album) entryList.getSelectedValue()).getMediaFiles());
                } else if (evt.getClickCount() == 3) {   // Triple-click
                    UserQueue.getInstance().enqueueAllFirst(((Album) entryList.getSelectedValue()).getMediaFiles());
                }
            } else if (entryList.getSelectedValue() instanceof MediaFile) {
                if (evt.getClickCount() == 2) {
                    UserQueue.getInstance().enqueueFirst((MediaFile) entryList.getSelectedValue());
                } else if (evt.getClickCount() == 3) {   // Triple-click
                    UserQueue.getInstance().enqueueFirst((MediaFile) entryList.getSelectedValue());
                }
            }
        }
    };


    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                if (entryList.getSelectedValue() instanceof Artist) {
                    showAlbumList((Artist) entryList.getSelectedValue());
                } else if (entryList.getSelectedValue() instanceof Album) {
                    showTitleList((Album) entryList.getSelectedValue());
                } else if (entryList.getSelectedValue() instanceof MediaFile) {
                    selectedMediaFile = (MediaFile) entryList.getSelectedValue();
                    updateLabels();
                }
            }
        }
    };

    private ListSelectionListener coverFlowSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            selectedAlbum = coverFlow.getSelectedAlbum();
            updateLabels();
        }
    };

    private ActionListener coverFlowActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showTitleList(coverFlow.getSelectedAlbum());
        }
    };

    private BasicPlayerListener uiPlayerListener = new BasicPlayerListener() {
        @Override
        public void opened(Object o, Map map) {

        }

        @Override
        public void progress(int i, long l, byte[] bytes, Map map) {
            updateProgressBar(l/1000000);
        }

        @Override
        public void stateUpdated(BasicPlayerEvent basicPlayerEvent) {
            int code = basicPlayerEvent.getCode();
            if (code == BasicPlayerEvent.PLAYING) {
                updateUIPlaying();
            } else if (code == BasicPlayerEvent.PAUSED) {
                updateUIPaused();
            } else if (code == BasicPlayerEvent.RESUMED) {
                updateUIPlaying();
            } else if (code == BasicPlayerEvent.STOPPED) {
                updateUIStopped();
            } else if (code == BasicPlayerEvent.EOM) {
            }
        }

        @Override
        public void setController(BasicController basicController) {

        }
    };

    private void updateProgressBar(long seconds) {
        timeSlider.setValue((int)seconds);
        updateTimeTextFrom((int)seconds);
    }

    private void updateTimeTextFrom(int sec) {
        int minutes = sec/60;
        int seconds = sec - (minutes*60);
        StringBuilder minText = new StringBuilder();
        StringBuilder secText = new StringBuilder();

        if(minutes < 10){
            minText.append("0");
        }
        if(seconds < 10){
            secText.append("0");
        }
        minText.append(minutes);
        secText.append(seconds);

        String currentText = timeText.getText();
        String maxText = currentText.substring(currentText.indexOf("/"));
        timeText.setText(minText.toString()+":"+secText+maxText);
    }

    private void updateTimeTextMax(int sec){
        int minutes = sec/60;
        int seconds = sec - (minutes*60);
        StringBuilder minText = new StringBuilder();
        StringBuilder secText = new StringBuilder();

        if(minutes < 10){
            minText.append("0");
        }
        if(seconds < 10){
            secText.append("0");
        }
        minText.append(minutes);
        secText.append(seconds);

        String currentText = timeText.getText();
        String fromText = currentText.substring(0,currentText.indexOf("/")-1);
        timeText.setText(fromText+" / "+minText+":"+secText);
    }

    private void updateUIPlaying() {
        playButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\pause_round_normal.png"), "ui-resources\\buttons\\pause_round_pressed.png", "ui-resources\\buttons\\pause_round_hover.png");
        setTitle("Playing: "+CurrentSongInformation.getInstance().getNowPlayingText());
    }

    private void updateUIStopped() {
        playButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\play_round_normal.png"), "ui-resources\\buttons\\play_round_pressed.png", "ui-resources\\buttons\\play_round_hover.png");
        setTitle(Constants.APPLICATION_NAME);
    }

    private void updateUIPaused() {
        playButton = new RoundButton(new ImageIcon("ui-resources\\buttons\\play_round_normal.png"), "ui-resources\\buttons\\play_round_pressed.png", "ui-resources\\buttons\\play_round_hover.png");
        setTitle("Paused: "+CurrentSongInformation.getInstance().getNowPlayingText());
    }

    private void updateLabels() {
        if (selectedArtist != null) {
            if (selectedArtist.getName().length() > 10) {
                artistLabel.setFont(new Font("Walkway Black", Font.PLAIN, 22));
            } else {
                artistLabel.setFont(new Font("Walkway Black", Font.PLAIN, 28));
            }
            artistLabel.setText(selectedArtist.getName());
        } else {
            artistLabel.setText(Constants.NO_ARTIST_TEXT);
        }

        if (selectedAlbum != null) {
            if (selectedAlbum.getName().length() > 10) {
                albumLabel.setFont(new Font("Walkway Black", Font.PLAIN, 22));
            } else {
                albumLabel.setFont(new Font("Walkway Black", Font.PLAIN, 28));
            }
            albumLabel.setText(selectedAlbum.getName());
        } else {
            albumLabel.setText(Constants.NO_ALBUM_TEXT);
        }

        if (selectedMediaFile != null) {
            if (selectedMediaFile.getTitle().length() > 10) {
                titleLabel.setFont(new Font("Walkway Black", Font.PLAIN, 22));
            } else {
                titleLabel.setFont(new Font("Walkway Black", Font.PLAIN, 28));
            }
            titleLabel.setText(selectedMediaFile.getTitle());
        } else {
            titleLabel.setText(Constants.NO_TITLE_TEXT);
        }
    }
}
