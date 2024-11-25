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
    @Override
    public List<Integer> getRecommendations(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        List<Collect> userCollects = collectMapper.findByUserId(userId);
        if (userCollects == null || userCollects.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Long, Integer> similarityScores = new HashMap<>();

        for (Collect collect : userCollects) {
//            System.out.println("collect: " + collect);
            if (collect.getSongId() == null) {
                continue;
            }

            for (Collect otherCollect : collectMapper.selectAll()) {
//                System.out.println("otherCollect: " + otherCollect);
                if (otherCollect == null || otherCollect.getSongId() == null) {
                    continue;
                }

                if (!collect.getUserId().equals(otherCollect.getUserId()) &&
                        collect.getSongId().equals(otherCollect.getSongId())) {
                    Long otherUserId = Long.valueOf(otherCollect.getUserId());
//                    System.out.println("otherUserId12121: " + otherUserId);
                    similarityScores.merge(otherUserId, 1, Integer::sum);
                }
            }
        }

//        // Sort users by similarity score
//        List<Map.Entry<Long, Integer>> sortedUsers = similarityScores.entrySet().stream()
//                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
//                .collect(Collectors.toList());
//
//        // Extract the user IDs from the sorted list
//        List<Integer> recommendedUserIds = new ArrayList<>();
//        for (Map.Entry<Long, Integer> entry : sortedUsers) {
//            recommendedUserIds.add(entry.getKey().intValue());
//        }


        // Sort users by similarity score
        List<Map.Entry<Long, Integer>> sortedUsers = similarityScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        // Get top N similar users
        List<Long> topNSimilarUsers = sortedUsers.stream()
                .limit(10) // Adjust the number based on your needs
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Get recommended songs from similar users
        List<Integer> recommendedSongs = topNSimilarUsers.stream()
                .flatMap(user -> collectMapper.findByUserId(user).stream()
                        .filter(collect -> collect.getSongId() != null)
                        .map(Collect::getSongId))
                .distinct()
                .collect(Collectors.toList());

        return recommendedSongs;
    }


}
