package com.example.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Collect;
import com.example.entity.Params;
import com.example.exception.CustomException;
import com.example.mapper.CollectMapper;
import com.example.service.CollectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CollectServiceImpl implements CollectService {
    @Resource
    private CollectMapper collectMapper;

    @Override
    public List<Collect> findAll() {
        return collectMapper.selectAll();
    }

    @Override
    public PageInfo<Collect> findBySearch(Params params) {
        // 开启分页查询
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Collect> list = collectMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Collect collect) {
        Collect collect1 = null;
        if (collect.getSongId() != null) {
            collect1 = collectMapper.finBySongId(collect.getSongId(), collect.getUserId());
        }
        if (collect.getSongListId() != null) {
            collect1 = collectMapper.finBySongListId(collect.getSongListId(), collect.getUserId());
        }
        if (collect1 != null) {
            throw new CustomException("该歌曲或歌单已被收藏");
        }
        String time = DateUtil.now();

        collect.setCreateTime(time);


        collectMapper.insertSelective(collect);
    }

    @Override
    public void update(Collect collect) {


        collectMapper.updateByPrimaryKeySelective(collect);
    }

    @Override
    public void delete(Integer id) {
        collectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Collect> findBySongId(Integer id) {
        return collectMapper.findBySongId(id);
    }

    @Override
    public List<Collect> findBySongListId(Integer id) {
        return collectMapper.findBySongListId(id);
    }

    @Override
    public List<Collect> findByConsumerId() {
        return collectMapper.findByConsumerId();
    }


    @Override
    public Collect findById(Integer id) {
        return collectMapper.selectByPrimaryKey(id);
    }


    //    @Override
//    public List<Integer> getRecommendations(Long userId) {
//        List<Collect> userCollects = collectMapper.findByUserId(userId);
//        Map<Long, Integer> similarityScores = new HashMap<>();
//
//        for (Collect collect : userCollects) {
//            for (Collect otherCollect : collectMapper.selectAll()) {
//                if (!collect.getUserId().equals(otherCollect.getUserId()) &&
//                        collect.getSongId() != null && collect.getSongId().equals(otherCollect.getSongId())) {
//                    Long otherUserId = Long.valueOf(otherCollect.getUserId());
//                    similarityScores.merge(otherUserId, 1, Integer::sum);
//                }
//            }
//        }
//
//        // Sort users by similarity score
//        List<Map.Entry<Long, Integer>> sortedUsers = similarityScores.entrySet().stream()
//                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
//                .collect(Collectors.toList());
//
//        // Get top N similar users
//        List<Long> topNSimilarUsers = sortedUsers.stream()
//                .limit(10) // Adjust the number based on your needs
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//
//        // Get recommended songs from similar users
//        List<Integer> recommendedSongs = topNSimilarUsers.stream()
//                .flatMap(user -> collectMapper.findByUserId(user).stream()
//                        .filter(collect -> collect.getSongId() != null)
//                        .map(Collect::getSongId))
//                .distinct()
//                .collect(Collectors.toList());
//
//        return recommendedSongs;
//    }
//    @Override
//    public List<Integer> getRecommendations(Long userId) {
//        if (userId == null) {
//            throw new IllegalArgumentException("User ID cannot be null");
//        }
//
//        List<Collect> userCollects = collectMapper.findByUserId(userId);
//        if (userCollects == null || userCollects.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        Map<Long, Integer> similarityScores = new HashMap<>();
//
//        for (Collect collect : userCollects) {
////            System.out.println("collect: " + collect);
//            if (collect.getSongId() == null) {
//                continue;
//            }
//
//            for (Collect otherCollect : collectMapper.selectAll()) {
////                System.out.println("otherCollect: " + otherCollect);
//                if (otherCollect == null || otherCollect.getSongId() == null) {
//                    continue;
//                }
//
//                if (!collect.getUserId().equals(otherCollect.getUserId()) &&
//                        collect.getSongId().equals(otherCollect.getSongId())) {
//                    Long otherUserId = Long.valueOf(otherCollect.getUserId());
////                    System.out.println("otherUserId12121: " + otherUserId);
//                    similarityScores.merge(otherUserId, 1, Integer::sum);
//                }
//            }
//        }
//
//        // Sort users by similarity score
//        List<Map.Entry<Long, Integer>> sortedUsers = similarityScores.entrySet().stream()
//                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
//                .collect(Collectors.toList());
//
//        // Get top N similar users
//        List<Long> topNSimilarUsers = sortedUsers.stream()
//                .limit(10) // Adjust the number based on your needs
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//
//        // Get recommended songs from similar users
//        List<Integer> recommendedSongs = topNSimilarUsers.stream()
//                .flatMap(user -> collectMapper.findByUserId(user).stream()
//                        .filter(collect -> collect.getSongId() != null)
//                        .map(Collect::getSongId)
//                )
//                .limit(10)
//                .distinct()
//                .collect(Collectors.toList());
//
//        return recommendedSongs;
//    }
    @Override
    public List<Integer> getRecommendations(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        List<Collect> userCollects = collectMapper.findByUserId(userId);
        if (userCollects == null || userCollects.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Long> userCollectedSongs = userCollects.stream()
                .map(collect -> {
                    Object songId = collect.getSongId();
                    // 假设 getSongId 可能返回 null 或者不是 Long 类型的数值，
                    // 我们在这里进行显式的 null 检查和类型转换。
                    return (songId != null) ? ((Number)songId).longValue() : null;
                })
                .filter(Objects::nonNull) // 过滤掉任何可能的 null 值
                .collect(Collectors.toSet());

        Map<Long, Integer> similarityScores = new HashMap<>();

        for (Collect collect : userCollects) {
            if (collect.getSongId() == null) {
                continue;
            }

            for (Collect otherCollect : collectMapper.selectAll()) {
                if (otherCollect == null || otherCollect.getSongId() == null) {
                    continue;
                }

                if (!collect.getUserId().equals(otherCollect.getUserId()) &&
                        collect.getSongId().equals(otherCollect.getSongId())) {
                    Long otherUserId = Long.valueOf(otherCollect.getUserId());
                    similarityScores.merge(otherUserId, 1, Integer::sum);
                }
            }
        }

        // Sort users by similarity score
        List<Map.Entry<Long, Integer>> sortedUsers = similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        // Get top N similar users
        List<Long> topNSimilarUsers = sortedUsers.stream()
                .limit(10) // Adjust the number based on your needs
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Get recommended songs from similar users, excluding songs already collected by the user
        List<Collect> recommendedCollects = topNSimilarUsers.stream()
                .flatMap(user -> collectMapper.findByUserId(user).stream()
                        .filter(collect -> collect.getSongId() != null && !userCollectedSongs.contains(collect.getSongId())))
                .collect(Collectors.toList());

        // Get all song IDs from recommended collects
        Map<Long, Long> songCollectCounts = recommendedCollects.stream()
                .collect(Collectors.groupingBy(
                        collect -> ((Number) collect.getSongId()).longValue(), // 显式转换为 Long
                        Collectors.counting()
                ));

        // Sort songs by collect count in descending order and get the top 10
        List<Map.Entry<Long, Long>> topSongs = songCollectCounts.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(10)
                .collect(Collectors.toList());

        // Convert to list of song IDs
        List<Integer> recommendedSongs = topSongs.stream()
                .map(Map.Entry::getKey)
                .mapToInt(Long::intValue)
                .distinct()
                .limit(10) // In case there are fewer than 10 unique songs
                .boxed()
                .collect(Collectors.toList());

        return recommendedSongs;
    }


}
