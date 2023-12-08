package com.nivek.kevoweather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class IconDownloader<T> extends HandlerThread {
    private static final String TAG ="IconDownloader" ;
    private Handler requestHandler ;
    private static final int DOWNLOAD  = 0 ;
    private ConcurrentHashMap<T,String> requestMap = new ConcurrentHashMap<>();
    private boolean hasQuit = false ;

    private  Handler responseHandler ;
    private  DownLoadListener<T> listener ;

    //a simple interface with a method that is called when download is done
    public  interface DownLoadListener<T>{
        void onIconDownloaded (T target , Bitmap bitmap) ;
    }

    //set a listener from the response thread
    public void setListener(DownLoadListener listener){
        this.listener = listener ;
        Log.i(TAG , "Listener was successfully set");
    }


    public IconDownloader(Handler aReponseHandler){
        super(TAG);
        this.responseHandler = aReponseHandler;
        Log.i(TAG ,"An instance of icondownloader was created");
    }

//    private static class RequestHandler extends  Handler{
//        @Override
//        public void handle(Message message){
//
//        }
//    }
    @Override
    public void onLooperPrepared(){
        Log.i(TAG,"A looper object was created") ;
        requestHandler = new Handler(){
            @Override
            public void handleMessage(Message message){
                Log.i(TAG, "tried to handle message");
                if(message.what == DOWNLOAD){
                    Log.i(TAG,"handleMessage was called");
                    T target = (T) message.obj ;
                    handleRequest(target) ;
                }
            }
        };
    }

    //handles the request of downloading the image given the target
    private void handleRequest(final T target){
        Log.i(TAG, "was called to handle request");
        try{
            //get the icon id string and construct a url for the download
            final String id = requestMap.get(target) ;
            Log.i(TAG,"handling ");
            if(id== null){
                Log.e(TAG,"id is null") ;
                return;
            }

            final String url = "https://openweathermap.org/img/wn/" + id + ".png" ;

            //get byte array for the icon and decode into bitmap
            byte[] iconByte = new WeatherDataFetcher().getUrlBytes(url) ;
            final Bitmap iconBitMap = BitmapFactory.decodeByteArray(iconByte, 0 , iconByte.length) ;

            Log.i(TAG,"got the bitmap from byte array");
            //pass the gotten icon and the target that where it belongs to the response handler
            responseHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG ,"tried running a task .... id gotten: "+ requestMap.get(target) +" and url is "+id) ;
                    if(requestMap.get(target) != id || hasQuit){
                        return ;
                    }

                    //remove the task from the hashmap and send the results to response thread
                    requestMap.remove(target) ;
                    Log.i(TAG,"successfully removed target from list");
                    listener.onIconDownloaded(target , iconBitMap);
                    Log.i(TAG,"updated main thread through its handler");
                }
            });
        }
        catch (IOException ioe){
            Log.e(TAG ,"error while downloading icon");
        }

    }

    public void queTask(T target , String url ){
        Log.i(TAG ,"try to assign task");
        if(url == null){
            requestMap.remove(target) ;
        }
        else{ // add it to the hashmap and to the messages for the thread handler
            Log.i(TAG,"really tried to"+url);
            requestMap.put(target , url) ;
            requestHandler.obtainMessage(DOWNLOAD,target).sendToTarget()  ;
        }
    }

    //stops the thread ; removes the messages  when the thread is no longer needed
    public void clearQueue(){
        requestHandler.removeMessages(DOWNLOAD);
        requestMap.clear();
    }


    //handles the quit operation
    @Override
    public boolean quit(){
        hasQuit = true ;
        return super.quit() ;
    }



}
