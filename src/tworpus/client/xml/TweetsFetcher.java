/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tworpus.client.xml;

import fetcher.FileManager;
import fetcher.FileManagerUpdateListener;
import fetcher.TweetFetcher;
import fetcher.TweetFetcherProgressListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter.Tweet;
import tworpus.client.visualization.VisualizationsController;

public class TweetsFetcher  implements FileManagerUpdateListener, TweetFetcherProgressListener {

    private int max_threads = 30;
    private TweetFetcher[] fetchers = new TweetFetcher[max_threads];
    private int startedThreads = 0;
    private int finishedThreads = 0;

    /**
     * @param outFolder Project folder
     */
    public TweetsFetcher(String outFolder) {
        FileManager manager = new FileManager(new File("tweets.csv"), this, outFolder);
        
        long start_with = 1;
        long tweets_to_fetch = manager.getLineCount();

        long step = (tweets_to_fetch - start_with) / max_threads;

        for (int i = 0; i < max_threads; i++) {
            fetchers[i] = new TweetFetcher(this, manager, i);
            fetchers[i].setParams(i * step, i * step + step, step, true, true, 0);
            fetchers[i].run();
        }
    }

    @Override
    public void onFileManagerError(String error) {
        System.out.println("File manager error");
    }

    @Override
    public void onProgressUpdate(long currentTweets, long allTweets, int id) {
        System.out.println("progress update");
    }

    @Override
    public void onStartDownloading() {
        System.out.println("Start Downloading");
        startedThreads++;
    }

    @Override
    public void onStopDownloading() {
        System.out.println("Stop Downloading");
        finishedThreads++;
        
        // All threads are finished --> download finished
        if(finishedThreads == startedThreads) {
            System.out.println("FINISHED DOWNLOAD --> merge");
            File projectDir = new File("tweets" + File.separator + "test");
            File xmlDir = new File(projectDir.getAbsolutePath() + File.separator + "xml");
            File newFile = new File(projectDir.getAbsolutePath() + File.separator + "tweets.xml");
            
            try {
                XMLFileMerger merger = new XMLFileMerger(xmlDir, null);
                File[] xmlFiles = xmlDir.listFiles();

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
    }

    @Override
    public void onTweetNotExsitingError(Tweet tweet) {
        System.out.println("NO TWEET EXISTS");
    }
}