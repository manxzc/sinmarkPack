//package cn.ymade.module_home.utils;
//
//import android.content.Context;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.SoundPool;
//import android.util.Log;
//
//import cn.ymade.module_home.R;
//
///**
// * Created by Administrator on 7/6/2017.
// */
//
//public class BeeperUtils {
//    public static final int BEEPER = 1;
//    public static final int BEEPER_SHORT = 2;
//    private static boolean mQuite = false;
//    private static boolean mBeepInventoried = false;
//    public static String BEEPER_MODEL = "beeper_model";
//    private static boolean mBeepPerTag = true;
//    private static final SoundPool mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,5);
//    private static  MediaPlayer mMediaPlayer = new MediaPlayer();
//
//    static {
////        int beeper = PreferenceUtil.getInt(BEEPER_MODEL,3);
////        int beeper =Sputils.getSpInt(CrossApp.get(),BEEPER_MODEL,3);
////        switch (beeper) {
////            case 0:
////                mQuite = mBeepInventoried = false;
////                mBeepPerTag = true;
////                break;
////            case 1:
////                mQuite = true;
////                mBeepPerTag = mBeepInventoried = false;
////                break;
////            case 2:
////                mBeepInventoried = true;
////                mQuite = mBeepPerTag = false;
////                break;
////            case 3:
//                mQuite = mBeepInventoried = false;
//                mBeepPerTag = true;
////                break;
////        }
//    }
//
//    public enum BeepMode{QUITE,BEEP_INVENTORIED,BEEP_PER_TAG};
//    public static void  init(Context context) {
////        Sputils.put(CrossApp.get(),BeeperUtils.BEEPER_MODEL, 3);
//        /**
//         * Must use SoundPool;
//         */
//       /* mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer player) {
//                player.seekTo(0);
//            }
//        });
//        try {
//        AssetFileDescriptor file = context.getResources().openRawResourceFd(
//                R.raw.beeper);
//            mMediaPlayer.setDataSource(file.getFileDescriptor(),
//                    file.getStartOffset(), file.getLength());
//            file.close();
//            mMediaPlayer.setVolume(1, 1);
//            mMediaPlayer.prepare();
//        } catch (IOException ioe) {
//            mMediaPlayer = null;
//            return;
//        }*/
//       // mSoundPool.load(context, R.raw.beeper,1);
//        mSoundPool.load(context, R.raw.scan_new,BEEPER);//scan_new
//        mSoundPool.load(context, R.raw.fail,BEEPER_SHORT);
//    }
//    public static void  beep(int soundID){
//        Log.i("TAG", "beep: mQuite "+mQuite+" mBeepInventoried "+mBeepInventoried+" mBeepPerTag "+mBeepPerTag);
//        if (mQuite) {
//            return;
//        }
//        if (soundID == BEEPER && mBeepInventoried) {
//            mSoundPool.play(BEEPER,1, 1, 0, 0, 1);
//            mBeepInventoried = false;
//        } else if (soundID == BEEPER_SHORT && mBeepPerTag) {
//            mSoundPool.play(BEEPER_SHORT,1, 1, 0, 0, 1);
//        } else if (soundID == BEEPER_SHORT && !mBeepPerTag){
//            mBeepInventoried = true;
//        } else {
//        }
//       /*if (mMediaPlayer != null) {
//            mMediaPlayer.start();
//        }*/
//    }
//
//    /**
//     * Set the beep mode.
//     * @param beepMode
//     */
//    public static void setBeepMode(BeepMode beepMode) {
//        switch (beepMode) {
//            case QUITE:
//                mQuite = true;
//                mBeepInventoried = mBeepPerTag = false;
//                break;
//            case BEEP_INVENTORIED:
//                mBeepInventoried = mQuite = mBeepPerTag = false;
//                break;
//            case BEEP_PER_TAG:
//                mBeepPerTag = true;
//                mQuite = mBeepInventoried = false;
//                break;
//            default:
//                mBeepInventoried = mQuite = mBeepPerTag = false;
//                break;
//        }
//
//    }
//
//    public static void release() {
//        /*if (mMediaPlayer != null) {
//            mMediaPlayer.release();
//        }*/
//        if (mSoundPool != null) {
//            mSoundPool.release();
//        }
//
//    }
//}
