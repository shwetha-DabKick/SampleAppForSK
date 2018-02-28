package com.powerpoint45.dtube;

import com.dabkick.videosdk.publicsettings.DabKickVideoInfo;
import com.dabkick.videosdk.publicsettings.DabKickVideoProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by iFocus on 28-02-2018.
 */

public class Utilities {

    public static ArrayList<String> getCategories(){

        ArrayList<String> categories = new ArrayList<>();

        categories.add("HOT");
        categories.add("Trending");
        categories.add("New");
        categories.add("History");
        categories.add("Subscribed");

        return categories;
    }

    public static ArrayList<DabKickVideoInfo> getCategoryVideos(int category){

        ArrayList<DabKickVideoInfo> videoInfo = new ArrayList<>();
        VideoArrayList allVideos = new VideoArrayList();
        VideoArrayList videos = new VideoArrayList();
        videos = allVideos.getCategorizedVideos(category);
        class SortVideos implements Comparator<Video>
        {
            // Used for sorting in ascending order of
            // roll number
            public int compare(Video a, Video b)
            {
                if (a.getDate() > b.getDate()) {
                    return  -1;
                } else {
                    return 1;
                }
            }
        }
        Collections.sort(videos, new SortVideos());

        for(Video vid : videos){

            DabKickVideoInfo catVideo = new DabKickVideoInfo(vid.user, vid.title, "",vid.getImageURL(),
                    vid.permlink);

            videoInfo.add(catVideo);

        }

        return videoInfo;
    }

    public static DabKickVideoProvider createDabKickVideoProvider(final DabKickVideoInfo selectedVideoInfo) {

        final ArrayList<String> videoCategories = getCategories();
        final Map<String, ArrayList<DabKickVideoInfo>> videoHolder = new LinkedHashMap<>();

        videoHolder.put(getCategories().get(0), getCategoryVideos(0));
        videoHolder.put(getCategories().get(1), getCategoryVideos(1));
        videoHolder.put(getCategories().get(2), getCategoryVideos(2));
        videoHolder.put(getCategories().get(3), getCategoryVideos(3));
        videoHolder.put(getCategories().get(4), getCategoryVideos(4));

        return new DabKickVideoProvider() {
            @Override
            public ArrayList<DabKickVideoInfo> provideVideos(String category, int offset) {
                int totalSize = videoHolder.get(category).size();
                int endIndex = Math.min(totalSize, offset + 3);
                ArrayList<DabKickVideoInfo> list = new ArrayList<>(videoHolder.get(category).subList(offset, endIndex));
                return list;
            }

            @Override
            public ArrayList<String> provideCategories(int offset) {
                if (offset == videoCategories.size()) {
                    // cannot provide any more video categories
                    return new ArrayList<>();
                }
                int endIndex = Math.min(videoCategories.size(), offset + 5);
                ArrayList<String> categoryList = new ArrayList<>(videoCategories.subList(offset, endIndex));
                return categoryList;
            }

            @Override
            public ArrayList<DabKickVideoInfo> startDabKickWithVideos() {
                ArrayList<DabKickVideoInfo> smallerList = new ArrayList<>();
                if (selectedVideoInfo != null) {
                    smallerList.add(selectedVideoInfo);
                }
                return smallerList;
            }
        };
    }
}
