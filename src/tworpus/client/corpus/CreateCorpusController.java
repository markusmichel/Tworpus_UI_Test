/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tworpus.client.corpus;

import fetcher.FileManager;
import fetcher.FileManagerUpdateListener;
import fetcher.TweetFetcher;
import fetcher.TweetFetcherProgressListener;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import twitter.Tweet;
import tworpus.client.visualization.VisualizationsController;
import tworpus.client.xml.NoSuchDirectoryException;
import tworpus.client.xml.XMLFileMerger;
import tworpus.client.xml.XMLMergerUpdateListener;

public class CreateCorpusController implements
        Initializable, FileManagerUpdateListener, TweetFetcherProgressListener,
        XMLMergerUpdateListener {

    @FXML
    private ProgressIndicator progressBar;
    @FXML
    private ProgressIndicator progressBarMerge;
    @FXML
    private Label labelDownloadTime;

    private final int max_threads = 30;
    private final TweetFetcher[] fetchers = new TweetFetcher[max_threads];
    private int startedThreads = 0;
    private int finishedThreads = 0;
    private long[] currentTweets = new long[max_threads];
    private long tweetsToFetch;
    
    private long startTime;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progressBar.setProgress(0);
        progressBarMerge.setProgress(0);
    }

    @FXML
    public void startFetching() {
        startTime = System.currentTimeMillis();
        // @TODO: dynamic paths
        String outFolder = "test";
        FileManager manager = new FileManager(new File("tweets.csv"), this, outFolder);

        long start_with = 1;
        tweetsToFetch = manager.getLineCount();

        long step = (tweetsToFetch - start_with) / max_threads;

        for (int i = 0; i < max_threads; i++) {
            fetchers[i] = new TweetFetcher(this, manager, i);
            fetchers[i].setParams(i * step, i * step + step, step, true, true, 0);
            fetchers[i].run();
        }
    }

    private void onDownloadFinished() {
        System.out.println("FINISHED DOWNLOAD --> merge");
        
        // Set download progress bar to 100 as it would never exactly reach it
        progressBar.setProgress(1);
        
        File projectDir = new File("tweets" + File.separator + "test");
        File xmlDir = new File(projectDir.getAbsolutePath() + File.separator + "xml");
        File newFile = new File(projectDir.getAbsolutePath() + File.separator + "tweets.xml");

        try {
            XMLFileMerger merger = new XMLFileMerger(xmlDir, newFile);
            merger.addUpdateListener(this);

            //newFile.getParentFile().mkdirs();
            //newFile.createNewFile();
            merger.openFile(newFile);
            merger.merge();
            merger.save();

            System.out.println("finished merging");

        } catch (NoSuchDirectoryException ex) {
            Logger.getLogger(VisualizationsController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    // Fetcher events
    @Override
    public void onFileManagerError(String error) {
        System.out.println("File manager error");
    }

    @Override
    public void onProgressUpdate(long currentTweets, long allTweets, int id) {
        this.currentTweets[id] = currentTweets;

        long now = getCurrentTweets();

        float percent = (100 / (float) tweetsToFetch) * now;
        percent /= 100;
        progressBar.setProgress(percent);
    }

    @Override
    public void onStartDownloading() {
        System.out.println("Start Downloading");
        startedThreads++;
    }

    @Override
    public void onStopDownloading() {
        finishedThreads++;

        // All threads are finished --> download finished
        if (finishedThreads == startedThreads) {
            onDownloadFinished();
        }
    }

    @Override
    public void onTweetNotExsitingError(Tweet tweet) {
        System.out.println("NO TWEET EXISTS");
    }

    private long getCurrentTweets() {
        long result = 0;
        for (int i = 0; i < currentTweets.length; i++) {
            result += currentTweets[i];
        }
        return result;
    }

    @Override
    /**
     * @param percentFinished current status between 0 and 1
     */
    public void onXmlMergerProgressChanged(float percentFinished) {
        progressBarMerge.setProgress(percentFinished);
        
        if(percentFinished == 1) {
            long end = System.currentTimeMillis();
            final long diff = end - startTime;
            final long s = diff / 1000;
            
            // Run later beacause of threads
            Platform.runLater(new Runnable(){ 
                @Override
                public void run() {
                    labelDownloadTime.setText("finished in " + s + "s");
                }
            });
            
        }
    }
}
