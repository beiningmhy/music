<template>
    <div style="max-height: 89vh;overflow: auto;" class="detail">
        <el-backtop target=".detail" style="z-index: 10000;" :bottom="120" :visibility-height="10"></el-backtop>
        <!-- 头部歌单详情介绍 -->
        <div style="height: 250px;width: 80%;margin:0 auto ;">
            <el-skeleton style="width: 100%" :loading="loading" animated>
                <template slot="template">

                    <div style="display: flex;height: 250px;width: 100%;">
                        <!-- 歌单照片 -->
                        <div style="width:250px; height: 250px;">
                            <el-skeleton-item variant="image" style="width:250px; height: 250px;" />

                        </div>
                        <!-- 歌单详细介绍 -->
                        <div
                            style=" height: 250px;width: 100px;flex: 1;display: flex;flex-direction: column;margin-left: 20px;">
                            <div style="height: 60px;padding-top: 10px;padding-left: 100px;">
                                <el-skeleton-item variant="h1" style="width: 200px;height: 40px;" />
                            </div>
                            <div style="height: 120px;overflow: hidden;flex: 1;">
                                <el-skeleton :rows="5" />
                            </div>
                            <div style="height: 50px;">
                                <el-skeleton-item variant="button" style="margin-right: 16px;width: 100px;" />
                            </div>
                        </div>
                    </div>
                </template>
                <template>
                    <div style="display: flex;height: 250px;width: 100%;">
                        <!-- 歌单照片 -->
                        <div style="width:250px; height: 250px;">
                            <el-image style="width:250px; height: 250px;border-radius: 10% ;"
                                :src="'http://localhost:8080/api/files/' + songList.pic"
                                :preview-src-list="['http://localhost:8080/api/files/' + songList.pic]">
                            </el-image>

                        </div>
                        <!-- 歌单详细介绍 -->
                        <div
                            style=" height: 250px;width: 100px;flex: 1;display: flex;flex-direction: column;margin-left: 20px;">
                            <div style="height: 60px;padding-top: 10px;padding-left: 100px;overflow: hidden;">
                                <span style="font-size: 30px;height: 60px;line-height: 60px;">{{ songList.title
                                    }}</span>
                            </div>
                            <div style="height: 100px;overflow: hidden;flex: 1;" @click="introductionDialog = true">
                                <span style="font-size: 16px;">{{ songList.introductions }}</span>
                            </div>
                            <div style="height: 30px;">
                                <!-- {{ averageScore }} -->
                                <el-rate :value="Number(averageScore)" :max=10 :low-threshold=4 :high-threshold=8
                                    disabled :colors="['#99A9AF', '#F7BA2A', '#FF5500']" show-score text-color="#ff9900"
                                    :score-template="`${averageScore}`">
                                </el-rate>
                            </div>
                            <div style="display: flex;width: 800px;">
                                <!-- <div style="height: 50px;">
                                    <el-button type="primary" style="margin: 0 auto;"
                                        @click="playAll()">播放全部</el-button>
                                </div> -->
                                <div class="play-btn" @click="playAll()"
                                    style="display:flex;height: 30px;width: 150px;padding-top: 5px;padding-left: 10px;overflow: hidden;">
                                    <div>
                                        <svg t="1731298449347" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                            xmlns="http://www.w3.org/2000/svg" p-id="1457" width="20" height="20">
                                            <path
                                                d="M783.74 401.86L372.23 155.28c-85.88-51.46-195.08 10.41-195.08 110.53v493.16c0 100.12 109.2 161.99 195.08 110.53l411.51-246.58c83.5-50.04 83.5-171.03 0-221.06z"
                                                p-id="1458"></path>
                                        </svg>
                                    </div>
                                    <span style="margin-left: 10px;">播放全部</span>
                                </div>
                                <!-- <div style="height: 50px;">
                                    <el-button type="primary" style="margin: 0 auto;margin-left: 30px;"
                                        @click="addSongList2Collect()">添加至收藏</el-button>
                                </div> -->
                                <div class="play-btn" @click="addSongList2Collect()"
                                    style="display:flex;height: 30px;width: 150px;padding-top: 5px;padding-left: 20px;margin-left: 10px;">
                                    <div>
                                        <svg t="1731299241051" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                            xmlns="http://www.w3.org/2000/svg" p-id="5668" width="20" height="20">
                                            <path
                                                d="M335.008 916.629333c-35.914667 22.314667-82.88 10.773333-104.693333-25.557333a77.333333 77.333333 0 0 1-8.96-57.429333l46.485333-198.24a13.141333 13.141333 0 0 0-4.021333-12.864l-152.16-132.586667c-31.605333-27.52-35.253333-75.648-8.234667-107.733333a75.68 75.68 0 0 1 51.733333-26.752L354.848 339.2c4.352-0.362667 8.245333-3.232 10.026667-7.594667l76.938666-188.170666c16.032-39.2 60.618667-57.92 99.52-41.461334a76.309333 76.309333 0 0 1 40.832 41.461334l76.938667 188.16c1.781333 4.373333 5.674667 7.253333 10.026667 7.605333l199.712 16.277333c41.877333 3.413333 72.885333 40.458667 69.568 82.517334a76.938667 76.938667 0 0 1-26.08 51.978666l-152.16 132.586667c-3.541333 3.082667-5.141333 8.074667-4.021334 12.853333l46.485334 198.24c9.621333 41.013333-15.36 82.336-56.138667 92.224a75.285333 75.285333 0 0 1-57.525333-9.237333l-170.976-106.24a11.296 11.296 0 0 0-12.010667 0l-170.986667 106.24zM551.786667 756.032l170.976 106.24c2.624 1.621333 5.717333 2.122667 8.650666 1.408 6.410667-1.557333 10.56-8.426667 8.928-15.424l-46.485333-198.24a77.141333 77.141333 0 0 1 24.277333-75.733333L870.293333 441.706667c2.485333-2.165333 4.053333-5.312 4.330667-8.746667 0.565333-7.136-4.490667-13.173333-10.976-13.696l-199.712-16.288a75.989333 75.989333 0 0 1-64.064-47.168l-76.938667-188.16a12.309333 12.309333 0 0 0-6.538666-6.741333c-5.898667-2.496-12.725333 0.373333-15.328 6.741333l-76.949334 188.16a75.989333 75.989333 0 0 1-64.064 47.168l-199.701333 16.288a11.68 11.68 0 0 0-7.978667 4.181333 13.226667 13.226667 0 0 0 1.333334 18.261334l152.16 132.586666a77.141333 77.141333 0 0 1 24.277333 75.733334l-46.485333 198.229333a13.333333 13.333333 0 0 0 1.514666 9.877333c3.488 5.792 10.581333 7.530667 16.064 4.128l170.986667-106.229333a75.296 75.296 0 0 1 79.562667 0z"
                                                fill="#000000" p-id="5669"></path>
                                        </svg>
                                    </div>
                                    <span style="margin-left: 10px;">添加至收藏</span>
                                </div>
                                <div class="play-btn" @click="initMark()"
                                    style="display:flex;height: 30px;width: 100px;padding-top: 5px;padding-left: 20px;margin-left: 10px;">
                                    <div>
                                        <svg t="1731554882465" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                            xmlns="http://www.w3.org/2000/svg" p-id="2350" width="20" height="20">
                                            <path
                                                d="M724.5 877.3c-7.8 0-15.5-2.9-21.5-8.3L575.8 753.4l-167.4 39.2c-11.9 2.7-24.3-1.4-32-10.8-7.8-9.4-9.5-22.4-4.5-33.5l70.7-156.7-89-147c-6.3-10.4-6.2-23.5 0.4-33.8 6.5-10.3 18.3-16 30.5-14.6L555.4 415l112.3-130.1c8-9.2 20.4-13.2 32.3-10 11.8 3 20.8 12.5 23.3 24.5l35 168.3 158.5 66.7c11.2 4.8 18.8 15.4 19.5 27.6 0.7 12.2-5.5 23.7-16.1 29.7l-149.3 85.2L756.4 848c-1 12.2-8.8 22.7-20.2 27.1-3.7 1.5-7.7 2.2-11.7 2.2zM584.9 686.4c7.9 0 15.6 2.9 21.5 8.3l91.7 83.3 10.4-123.5c0.9-10.5 6.9-19.9 16-25.1L832.1 568l-114.2-48c-9.7-4.1-16.8-12.7-18.9-23l-25.2-121.3-81 93.8c-6.9 8-17.3 11.9-27.7 10.9L442 466.8l64.2 106c5.5 9 6.1 20.1 1.8 29.8l-51 112.9 120.6-28.3c2.4-0.5 4.9-0.8 7.3-0.8z"
                                                p-id="2351"></path>
                                            <path
                                                d="M637.4 268.8c-5.5-10.7-16.6-17.9-28.6-17.9l-142.9-0.9-80.6-118.2c-6.8-9.8-18.8-15.4-30.7-13.7-12.4 1.7-22.2 10.2-26 21.8l-44.8 135.7-137.4 39.7c-11.9 3.4-20.5 13.2-22.6 25.2-2.1 11.9 2.6 24.3 12.4 31.6l115.2 84.5-4.4 142.9c-0.4 12.4 6 23.5 16.6 29.4 4.7 2.6 10.2 3.8 15.4 3.8 6.4 0 12.8-2.1 18.3-6.4l98.6-70.4-33.3-55-49.9 35.8 3-94.7c0.4-10.7-4.3-20.9-12.8-26.9l-76.4-56.3 90.9-26.5c10.2-3 18.3-10.7 21.3-20.9l30.3-90 53.3 78.5c6 9 15.8 14.1 26 14.1l95.1 0.4-55.9 71.7 74.9 8.1 71.1-91.7c4.7-5.1 6.8-12.4 6.8-18.8 0.1-5.1-0.7-10.2-2.9-14.9z"
                                                fill="#FFCE00" p-id="2352"></path>
                                        </svg>
                                    </div>
                                    <span style="margin-left: 10px;">评分</span>
                                </div>
                            </div>

                        </div>
                    </div>

                </template>
            </el-skeleton>
        </div>
        <!-- 歌手歌曲 -->
        <div>
            <div>
                <el-tooltip class="item" effect="dark" content="单击歌曲查看歌曲详情，双击添加至播放列表" placement="left">
                    <h1 style="font-size:25px;width: 100px;">歌曲 ></h1>
                </el-tooltip>
            </div>
            <div>
                <el-pagination @current-change="handleCurrentChange2" background layout="prev, pager, next"
                    :total="total2" :page-size="params2.pageSize" :current-page="params2.pageNum">
                </el-pagination>
                <el-table v-loading="songLoading" :data="tableData" style="width: 100%;background-color: transparent ;"
                    @row-dblclick="songDbClick($event)">
                    <el-table-column prop="name" label="歌曲" width="400">
                        <template v-slot="scope">
                            <div style="display: flex;">
                                <div>
                                    <el-image style="width: 30px; height: 30px; border-radius: 10%"
                                        :src="'http://localhost:8080/api/files/' + scope.row.pic"
                                        :preview-src-list="['http://localhost:8080/api/files/' + scope.row.pic]">
                                    </el-image>
                                </div>
                                <div style="text-align: center;display: inline-block;height: 30px;line-height: 30px;"
                                    @click="songClick(scope.row)">
                                    <span style="margin-left: 10px;font-weight: 600;font-size: 15px;"
                                        class="music-name">
                                        {{ scope.row.name }}</span>
                                </div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="singerName" label="歌手">
                    </el-table-column>
                    <el-table-column prop="introductions" label="专辑">
                    </el-table-column>
                    <el-table-column prop="audioDuration" label="时长">
                    </el-table-column>
                    <el-table-column label="操作" width="100">
                        <template slot-scope="scope">
                            <div style="width: 30px;height: 30px;margin-top: 10px;margin-right: 10px; padding-top: 5px;padding-left: 5px;"
                                class="detail-icon">
                                <el-popover placement="left" width="200" trigger="hover" class="detail-popover">
                                    <div style="display: flex;flex-direction: column;">
                                        <div style="height: 50px;width: 100%;display: flex;">
                                            <div style="width: 40px;height: 40px;" @click="songDbClick(scope.row)">
                                                <el-image
                                                    style="width: 40px; height: 40px; border-radius: 10%;margin-top: 5px;"
                                                    :src="'http://localhost:8080/api/files/' + scope.row.pic">
                                                </el-image>
                                            </div>
                                            <div
                                                style="height: 80%;width: 190px;display: flex;margin-left: 10px;margin-top: 5px;flex-direction: column;flex: 1;">
                                                <div style="margin: 0 auto;width: 100%;height: 50%;overflow: hidden;font-size: 14px;"
                                                    @click="clickMusicName(scope.row)" class="music-name">
                                                    {{ scope.row.name }}
                                                </div>
                                                <div style="margin: 0 auto;width: 100%;height: 40%;overflow: hidden;font-size: 10px;margin-top: 7px"
                                                    @click="clickSingerName(scope.row)" class="singer-name">
                                                    {{ scope.row.singerName }}——{{ scope.row.introductions }}
                                                </div>

                                            </div>
                                        </div>
                                        <div style="border-bottom:1px darkgray solid;"></div>
                                        <div class="play-btn" @click="songDbClick(scope.row)"
                                            style="display:flex;height: 30px;width: 90%;padding-top: 5px;padding-left: 10px;margin: 2px auto;">
                                            <div>
                                                <svg t="1731298449347" class="icon" viewBox="0 0 1024 1024"
                                                    version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1457"
                                                    width="20" height="20">
                                                    <path
                                                        d="M783.74 401.86L372.23 155.28c-85.88-51.46-195.08 10.41-195.08 110.53v493.16c0 100.12 109.2 161.99 195.08 110.53l411.51-246.58c83.5-50.04 83.5-171.03 0-221.06z"
                                                        p-id="1458"></path>
                                                </svg>
                                            </div>
                                            <span style="margin-left: 10px;">播放</span>
                                        </div>
                                        <div class="play-btn" @click="removeItem(scope.row)"
                                            style="display:flex;height: 30px;width: 90%;padding-top: 5px;padding-left: 10px;margin: 2px auto;">
                                            <div>
                                                <svg t="1731298766910" class="icon" viewBox="0 0 1024 1024"
                                                    version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1623"
                                                    width="20" height="20">
                                                    <path
                                                        d="M857.99 286.99H724.6v-27.6c0-62.53-50.87-113.4-113.4-113.4H409.78c-62.53 0-113.4 50.87-113.4 113.4v27.6h-133.4c-23.47 0-42.5 19.03-42.5 42.5s19.03 42.5 42.5 42.5h53.25L265.87 837c2.31 21.6 20.53 37.99 42.26 37.99h404c21.7 0 39.92-16.35 42.25-37.93l50.29-465.07h53.31c23.47 0 42.5-19.03 42.5-42.5s-19.02-42.5-42.49-42.5z m-476.61-27.6c0-15.66 12.74-28.4 28.4-28.4H611.2c15.66 0 28.4 12.74 28.4 28.4v27.6H381.38v-27.6z m292.6 530.6H346.34l-44.62-418h417.46l-45.2 418z"
                                                        p-id="1624"></path>
                                                    <path
                                                        d="M430.99 445.99c-23.47 0-42.5 19.03-42.5 42.5v146c0 23.47 19.03 42.5 42.5 42.5s42.5-19.03 42.5-42.5v-146c0-23.47-19.03-42.5-42.5-42.5zM589.99 445.99c-23.47 0-42.5 19.03-42.5 42.5v146c0 23.47 19.03 42.5 42.5 42.5s42.5-19.03 42.5-42.5v-146c0-23.47-19.03-42.5-42.5-42.5z"
                                                        p-id="1625"></path>
                                                </svg>
                                            </div>
                                            <span style="margin-left: 10px;">从播放列表中移除</span>
                                        </div>
                                        <div style="border-bottom:1px darkgray solid;"></div>
                                        <div class="play-btn" @click="addSong2Collect(scope.row)"
                                            style="display:flex;height: 30px;width: 90%;padding-top: 5px;padding-left: 10px;margin: 2px auto;">
                                            <div>
                                                <svg t="1731299241051" class="icon" viewBox="0 0 1024 1024"
                                                    version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5668"
                                                    width="20" height="20">
                                                    <path
                                                        d="M335.008 916.629333c-35.914667 22.314667-82.88 10.773333-104.693333-25.557333a77.333333 77.333333 0 0 1-8.96-57.429333l46.485333-198.24a13.141333 13.141333 0 0 0-4.021333-12.864l-152.16-132.586667c-31.605333-27.52-35.253333-75.648-8.234667-107.733333a75.68 75.68 0 0 1 51.733333-26.752L354.848 339.2c4.352-0.362667 8.245333-3.232 10.026667-7.594667l76.938666-188.170666c16.032-39.2 60.618667-57.92 99.52-41.461334a76.309333 76.309333 0 0 1 40.832 41.461334l76.938667 188.16c1.781333 4.373333 5.674667 7.253333 10.026667 7.605333l199.712 16.277333c41.877333 3.413333 72.885333 40.458667 69.568 82.517334a76.938667 76.938667 0 0 1-26.08 51.978666l-152.16 132.586667c-3.541333 3.082667-5.141333 8.074667-4.021334 12.853333l46.485334 198.24c9.621333 41.013333-15.36 82.336-56.138667 92.224a75.285333 75.285333 0 0 1-57.525333-9.237333l-170.976-106.24a11.296 11.296 0 0 0-12.010667 0l-170.986667 106.24zM551.786667 756.032l170.976 106.24c2.624 1.621333 5.717333 2.122667 8.650666 1.408 6.410667-1.557333 10.56-8.426667 8.928-15.424l-46.485333-198.24a77.141333 77.141333 0 0 1 24.277333-75.733333L870.293333 441.706667c2.485333-2.165333 4.053333-5.312 4.330667-8.746667 0.565333-7.136-4.490667-13.173333-10.976-13.696l-199.712-16.288a75.989333 75.989333 0 0 1-64.064-47.168l-76.938667-188.16a12.309333 12.309333 0 0 0-6.538666-6.741333c-5.898667-2.496-12.725333 0.373333-15.328 6.741333l-76.949334 188.16a75.989333 75.989333 0 0 1-64.064 47.168l-199.701333 16.288a11.68 11.68 0 0 0-7.978667 4.181333 13.226667 13.226667 0 0 0 1.333334 18.261334l152.16 132.586666a77.141333 77.141333 0 0 1 24.277333 75.733334l-46.485333 198.229333a13.333333 13.333333 0 0 0 1.514666 9.877333c3.488 5.792 10.581333 7.530667 16.064 4.128l170.986667-106.229333a75.296 75.296 0 0 1 79.562667 0z"
                                                        fill="#000000" p-id="5669"></path>
                                                </svg>
                                            </div>
                                            <span style="margin-left: 10px;">添加至收藏</span>
                                        </div>
                                        <div class="play-btn" @click="copyText(scope.row)"
                                            style="display:flex;height: 30px;width: 90%;padding-top: 5px;padding-left: 10px;margin: 2px auto;">
                                            <div>
                                                <svg t="1731299271839" class="icon" viewBox="0 0 1024 1024"
                                                    version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5830"
                                                    width="20" height="20">
                                                    <path
                                                        d="M394.666667 106.666667h448a74.666667 74.666667 0 0 1 74.666666 74.666666v448a74.666667 74.666667 0 0 1-74.666666 74.666667H394.666667a74.666667 74.666667 0 0 1-74.666667-74.666667V181.333333a74.666667 74.666667 0 0 1 74.666667-74.666666z m0 64a10.666667 10.666667 0 0 0-10.666667 10.666666v448a10.666667 10.666667 0 0 0 10.666667 10.666667h448a10.666667 10.666667 0 0 0 10.666666-10.666667V181.333333a10.666667 10.666667 0 0 0-10.666666-10.666666H394.666667z m245.333333 597.333333a32 32 0 0 1 64 0v74.666667a74.666667 74.666667 0 0 1-74.666667 74.666666H181.333333a74.666667 74.666667 0 0 1-74.666666-74.666666V394.666667a74.666667 74.666667 0 0 1 74.666666-74.666667h74.666667a32 32 0 0 1 0 64h-74.666667a10.666667 10.666667 0 0 0-10.666666 10.666667v448a10.666667 10.666667 0 0 0 10.666666 10.666666h448a10.666667 10.666667 0 0 0 10.666667-10.666666v-74.666667z"
                                                        fill="#000000" p-id="5831"></path>
                                                </svg>
                                            </div>
                                            <span style="margin-left: 10px;">复制歌曲名</span>
                                        </div>
                                        <div class="play-btn" @click="downUrl(scope.row.audioUrl)"
                                            style="display:flex;height: 30px;width: 90%;padding-top: 5px;padding-left: 10px;margin: 2px auto;">
                                            <div>
                                                <svg t="1731299458029" class="icon" viewBox="0 0 1024 1024"
                                                    version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3267"
                                                    width="20" height="20">
                                                    <path
                                                        d="M832 768v64H192v-64H128v128h768v-128zM822.624 438.624l-45.248-45.248L544 626.752V128h-64v498.752l-233.376-233.376-45.248 45.248L512 749.248z"
                                                        fill="#181818" p-id="3268"></path>
                                                </svg>
                                            </div>
                                            <span style="margin-left: 10px;">下载</span>
                                        </div>
                                    </div>

                                    <div slot="reference">
                                        <svg t="1731295289629" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                            xmlns="http://www.w3.org/2000/svg" p-id="3208" width="20" height="20">
                                            <path d="M509.9 512m-111 0a111 111 0 1 0 222 0 111 111 0 1 0-222 0Z" fill=""
                                                p-id="3209"></path>
                                            <path d="M849.1 512m-111 0a111 111 0 1 0 222 0 111 111 0 1 0-222 0Z" fill=""
                                                p-id="3210"></path>
                                            <path d="M174.9 512m-111 0a111 111 0 1 0 222 0 111 111 0 1 0-222 0Z" fill=""
                                                p-id="3211"></path>
                                        </svg>
                                    </div>
                                </el-popover>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination @current-change="handleCurrentChange2" background layout="prev, pager, next"
                    :total="total2" :page-size="params2.pageSize" :current-page="params2.pageNum">
                </el-pagination>
            </div>
        </div>
        <div>
            <div>
                <h1 style="font-size:25px;">评论区 ></h1>
            </div>
            <div>
                <!-- 这里是发表评论的地方，尚未开发 -->
                <div class="container">
                    <!-- <label for="styled-textarea">请输入您的文本:</label> -->
                    <textarea id="styled-textarea" name="styled-textarea" rows="10" cols="50" placeholder="在这里输入您的评论..."
                        v-model="comment"></textarea>
                    <div>
                        <el-button type="primary" style="margin: 0 auto; margin-top: 10px;"
                            @click="publishComment()">发表评论</el-button>
                    </div>
                </div>

            </div>
            <!-- 所有评论 -->
            <div style="margin-top: 10px;">
                <div v-if="commentList.length == 0">
                    <div style="width: 60%;height: 80px;margin: 0 auto;text-align: center;">
                        <h1>暂无评论</h1>
                    </div>

                </div>
                <div v-else>
                    <div v-for="item in commentList" :key="item.id">
                        <div
                            style="display: flex;width: 60%;height: 80px;margin: 0 auto;border-top: 1px solid rgb(162 162 162);">
                            <div style="width: 100px;">
                                <!-- 头像 -->
                                <el-image style="width: 60px; height: 60px; border-radius: 10%;margin-top: 10px;"
                                    :src="'http://localhost:8080/api/files/' + item.userAvatar"
                                    :preview-src-list="['http://localhost:8080/api/files/' + item.userAvatar]">
                                </el-image>
                            </div>
                            <div style="flex: 1;display: flex;flex-direction: column;">
                                <div style="flex: 1;overflow: hidden;padding-top: 5px;padding-left: 5px;"
                                    @click="commentForm = item; commentDialog = true;">
                                    <span style="font-size: 20px;color: blue;">{{ item.username }}:</span>
                                    <span style="white-space:pre-wrap;word-wrap: break-word;">
                                        {{ item.content }}
                                    </span>
                                </div>
                                <div style="height: 30%;">
                                    <div style="float: left;">
                                        {{ item.createTime }}
                                    </div>
                                    <div style="float: right;display: flex;">
                                        <div @click="up(item)" style="margin-right: 10px;">
                                            <svg t="1728798081711" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                                xmlns="http://www.w3.org/2000/svg" p-id="1477" width="20" height="20">
                                                <path
                                                    d="M896 341.333333a42.666667 42.666667 0 0 1 41.642667 51.925334l-85.333334 469.333333A42.666667 42.666667 0 0 1 810.666667 896h-298.666667c-8.448 0-16.682667-2.517333-23.68-7.168L328.405333 810.666667H128a42.666667 42.666667 0 0 1-42.666667-42.666667V384a42.666667 42.666667 0 0 1 42.666667-42.666667h189.141333l158.250667-235.306666A42.666667 42.666667 0 0 1 512 85.333333h21.333333C592.170667 85.333333 640 170.666667 640 192L682.666667 341.333333h213.333333z m-371.114667 469.333334h251.562667l66.346667-384H640a42.666667 42.666667 0 0 1-42.666667-42.666667l-42.666666-192a21.418667 21.418667 0 0 0-18.645334-21.162667L384 395.818667v349.354666L524.885333 810.666667zM298.666667 426.666667H170.666667v298.666666h128v-298.666666z"
                                                    fill="#6CCAFF" p-id="1478"></path>
                                            </svg>
                                            <el-badge :value="item.up" class="item">
                                            </el-badge>
                                        </div>
                                        <div @click="down(item)">
                                            <svg t="1728798866603" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                                xmlns="http://www.w3.org/2000/svg" p-id="1641" width="20" height="20">
                                                <path
                                                    d="M128 682.666667a42.666667 42.666667 0 0 1-41.642667-51.925334l85.333334-469.333333A42.666667 42.666667 0 0 1 213.333333 128h341.333334c8.448 0 16.64 2.517333 23.68 7.168L695.594667 213.333333H896a42.666667 42.666667 0 0 1 42.666667 42.666667v384a42.666667 42.666667 0 0 1-42.666667 42.666667h-189.141333l-158.293334 235.306666A42.666667 42.666667 0 0 1 512 938.666667h-21.333333C431.786667 938.666667 384 853.333333 384 832L341.333333 682.666667H128z m413.781333-469.333334H247.552l-66.346667 384H384a42.666667 42.666667 0 0 1 42.666667 42.666667l42.666666 192c0 10.837333 8.106667 19.797333 18.645334 21.162667L640 628.181333V278.826667L541.781333 213.333333zM725.333333 597.333333h128V298.666667h-128v298.666666z"
                                                    fill="#d81e06" p-id="1642"></path>
                                            </svg>
                                            <el-badge :value="item.down" class="item">
                                            </el-badge>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <el-pagination @current-change="handleCurrentChange3" background layout="prev, pager, next"
                        :total="total3" :page-size="params3.pageSize" :current-page="params3.pageNum">
                    </el-pagination>
                </div>


            </div>
        </div>

        <el-dialog :title="songList.title" :visible.sync="introductionDialog" width="30%"
            style="text-align: center;border-radius: 30% !important;">
            <div style="text-align: left;">
                <span>{{ songList.introduction }}</span>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button @click="introductionDialog = false">取 消</el-button>
                <el-button type="primary" @click="introductionDialog = false">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog :title="commentForm.username" :visible.sync="commentDialog" width="40%"
            style="text-align: center;border-radius: 30% !important;">

            <div style="text-align: left;">
                <div style="max-height: 300px;overflow-y: auto;">
                    评论内容：<br>
                    <div style="padding-left: 50px;">
                        <span style="white-space:pre-wrap;word-wrap: break-word;font-size: 20px;">
                            {{ commentForm.content }}</span>
                    </div>

                </div>

                <div style="height: 30%;">
                    <div style="float: left;">
                        {{ commentForm.createTime }}
                    </div>
                    <div style="float: right;display: flex;">
                        <div @click="up(commentForm)" style="margin-right: 10px;">
                            <svg t="1728798081711" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="1477" width="20" height="20">
                                <path
                                    d="M896 341.333333a42.666667 42.666667 0 0 1 41.642667 51.925334l-85.333334 469.333333A42.666667 42.666667 0 0 1 810.666667 896h-298.666667c-8.448 0-16.682667-2.517333-23.68-7.168L328.405333 810.666667H128a42.666667 42.666667 0 0 1-42.666667-42.666667V384a42.666667 42.666667 0 0 1 42.666667-42.666667h189.141333l158.250667-235.306666A42.666667 42.666667 0 0 1 512 85.333333h21.333333C592.170667 85.333333 640 170.666667 640 192L682.666667 341.333333h213.333333z m-371.114667 469.333334h251.562667l66.346667-384H640a42.666667 42.666667 0 0 1-42.666667-42.666667l-42.666666-192a21.418667 21.418667 0 0 0-18.645334-21.162667L384 395.818667v349.354666L524.885333 810.666667zM298.666667 426.666667H170.666667v298.666666h128v-298.666666z"
                                    fill="#6CCAFF" p-id="1478"></path>
                            </svg>
                            <el-badge :value="commentForm.up" class="item">
                            </el-badge>
                        </div>
                        <div @click="down(commentForm)">
                            <svg t="1728798866603" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="1641" width="20" height="20">
                                <path
                                    d="M128 682.666667a42.666667 42.666667 0 0 1-41.642667-51.925334l85.333334-469.333333A42.666667 42.666667 0 0 1 213.333333 128h341.333334c8.448 0 16.64 2.517333 23.68 7.168L695.594667 213.333333H896a42.666667 42.666667 0 0 1 42.666667 42.666667v384a42.666667 42.666667 0 0 1-42.666667 42.666667h-189.141333l-158.293334 235.306666A42.666667 42.666667 0 0 1 512 938.666667h-21.333333C431.786667 938.666667 384 853.333333 384 832L341.333333 682.666667H128z m413.781333-469.333334H247.552l-66.346667 384H384a42.666667 42.666667 0 0 1 42.666667 42.666667l42.666666 192c0 10.837333 8.106667 19.797333 18.645334 21.162667L640 628.181333V278.826667L541.781333 213.333333zM725.333333 597.333333h128V298.666667h-128v298.666666z"
                                    fill="#d81e06" p-id="1642"></path>
                            </svg>
                            <el-badge :value="commentForm.down" class="item">
                            </el-badge>
                        </div>
                    </div>
                </div>
            </div>

            <span slot="footer" class="dialog-footer">
                <!-- <el-button @click="commentDialog = false">取 消</el-button> -->
                <el-button type="primary" @click="commentDialog = false">关闭</el-button>
            </span>
        </el-dialog>


        <el-dialog title="请对此歌单评分" :visible.sync="markDialog" width="30%"
            style="text-align: center;border-radius: 30% !important;" top="40vh">
            <div style="height: 30px;">
                <!-- {{ mark }} -->
                <el-rate v-model="mark" :max=10 :low-threshold=4 :high-threshold=8
                    :colors="['#99A9AF', '#F7BA2A', '#FF5500']" show-score text-color="#ff9900"
                    :score-template="`${mark}`">
                </el-rate>
                <!-- <el-rate v-model="mark" :max=10 :low-threshold=4 :high-threshold=8
                    :colors="['#99A9AF', '#F7BA2A', '#FF5500']" show-score text-color="#ff9900"
                    :score-template="`${mark}`">
                </el-rate> -->
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button @click="markDialog = false">取 消</el-button>
                <el-button type="primary" @click="submitMark">确 定</el-button>
            </span>
        </el-dialog>
        <div style="height: 15vh;">

        </div>
    </div>

</template>
<script>
import request from '@/utils/request';

export default {
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : '',
            songListId: this.$route.query.songListId ? this.$route.query.songListId : '',
            params: {
                pageNum: 1,
                pageSize: 1,
            },
            params2: {
                pageNum: 1,
                pageSize: 10,
            },
            params3: {
                pageNum: 1,
                pageSize: 5,
            },
            loading: true,
            songList: [],
            introductionDialog: false,
            total2: 0,
            tableData: [],
            commentList: [],
            total3: 0,
            songLoading: true,
            averageScore: 0,
            comment: '',
            commentDialog: false,
            commentForm: {},
            markDialog: false,
            mark: null,

        }
    },
    // props: {
    //     singer: {
    //         type: Object,
    //         required: true
    //     }
    // },
    created() {

    },
    async mounted() {
        await this.initSongListDetail();
        await this.initSongs();
        await this.initComments();
        await this.avgScore();
    },

    methods: {
        async initSongListDetail() {
            this.params.songListId = this.songListId;
            await request.get("/songList/search", {
                params: this.params
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data)
                    this.loading = false;
                    let temp = [];
                    temp = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        introductions: item.introduction != null && item.introduction.length > 200 ? item.introduction.substring(0, 200) + '...' : item.introduction,
                        // birth: new Date(item.birth), 
                    }));

                    this.songList = temp[0];
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        async initSongs() {
            this.params2.status = '0';
            this.params2.songListId = this.songListId;
            try {
                const res = await request.get("/song/search", {
                    params: this.params2
                });

                if (res.code === '0') {
                    // console.log(res.data);
                    this.loading = false;

                    // 获取数据列表
                    const data = res.data.list;

                    // 异步处理每个项目的音频时长
                    this.tableData = await Promise.all(
                        data.map(async item => {
                            let audioUrl = '';
                            if (item.url != null) {
                                if (item.url.includes('|')) {
                                    audioUrl = 'http://localhost:8080/api/files/' + item.url;
                                } else {
                                    audioUrl = item.url;
                                }
                            } else {
                                audioUrl = '';

                            }

                            try {
                                const audioDuration = await this.getAudioDuration(audioUrl);
                                return {
                                    ...item,
                                    sts: item.status === '0',
                                    introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                                    audioDuration: this.formatDuration(audioDuration),
                                    audioDurationSeconds: audioDuration,
                                    audioUrl: audioUrl,
                                };
                            } catch (error) {
                                // console.error(`Failed to load audio file for item ${item.id}:`, error);
                                return {
                                    ...item,
                                    sts: item.status === '0',
                                    introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                                    audioDuration: null // 或者设置为其他默认值
                                };
                            }
                        })
                    );

                    this.total2 = res.data.total;
                    this.songLoading = false;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            } catch (error) {
                console.error('Error fetching data:', error);
                this.$message({
                    message: '请求失败',
                    type: 'error'
                });
            }
        },
        async initComments() {
            this.params3.radioId = this.songListId;
            this.params3.radio = '歌单';
            await request.get("/comment/search", {
                params: this.params3
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data)
                    this.loading = false;
                    let temp = [];
                    temp = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                    }))
                    this.commentList = temp;
                    this.total3 = res.data.total;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        getAudioDuration(url) {
            return new Promise((resolve, reject) => {
                const audio = new Audio(url);
                audio.addEventListener('loadedmetadata', () => {
                    resolve(Math.floor(audio.duration));
                });
                audio.addEventListener('error', (event) => {
                    // console.error('Audio error event:', event);
                    reject(new Error('Failed to load audio file'));
                });
            });
        },
        formatDuration(seconds) {
            if (seconds === null || seconds === undefined) {
                return '未知';
            }
            const minutes = Math.floor(seconds / 60);
            const remainingSeconds = seconds % 60;
            return `${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;
        },

        handleCurrentChange2(pageNum) {
            this.params2.pageNum = pageNum;
            this.initSongs();
        },
        handleCurrentChange3(pageNum) {
            this.params3.pageNum = pageNum;
            this.initComments();
        },
        up(item) {
            // console.log(item);
            request.post("/comment/up", item).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '点赞成功',
                        type: 'success',
                        duration: 500
                    });
                    this.initComments();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        down(item) {
            request.post("/comment/down", item).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '反对成功',
                        type: 'warning',
                        duration: 500
                    });
                    this.initComments();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        songClick(item) {
            // console.log(item);
            this.$router.push({
                path: '/songDetails',
                query: {
                    songId: item.id
                }
            })


        },
        songDbClick(item) {
            this.$store.commit('updateIsplay', false);
            localStorage.setItem("playingMusic", JSON.stringify(item));
            // 首先从 localStorage 获取现有的 musicList
            let musicList = localStorage.getItem('musicList');

            // 将获取到的字符串转换为数组
            if (musicList) {
                musicList = JSON.parse(musicList);
            } else {
                // 如果 musicList 不存在，则初始化为空数组
                musicList = [];
            }

            // 检查是否存在相同的 item 并删除
            const index = musicList.findIndex(existingItem => existingItem.id === item.id);
            if (index !== -1) {
                // 删除已存在的 item
                musicList.splice(index, 1);
            }

            // 将新的 item 放在数组的最前面
            musicList.unshift(item);

            // 将更新后的数组转换回 JSON 字符串并保存到 localStorage
            localStorage.setItem('musicList', JSON.stringify(musicList));
        },
        avgScore() {
            this.params.songListId = this.songListId;
            request.get("/rankList/avg", {
                params: this.params
            }).then(res => {
                if (res.code === '0') {
                    this.averageScore = res.data ? res.data.toFixed(2) : 0;
                    // console.log(this.averageScore);

                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        publishComment() {
            // console.log(this.comment);
            if (this.comment == '') {
                this.$message({
                    message: '评论不能为空',
                    type: 'error'
                });
                return;
            }
            let c = {
                songListId: this.songListId,
                content: this.comment,
            }
            if (this.user == '') {
                c.userId = 0;
            } else {
                c.userId = this.user.id;
            }
            request.post("/comment/save", c).then(res => {
                if (res.code == '0') {
                    this.$message({
                        message: '评论成功',
                        type: 'success'
                    });
                    this.comment = '';
                    this.initComments();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        playAll() {
            this.$store.commit('updateIsplay', false);
            if (this.tableData.length != 0) {
                // 首先从 localStorage 获取现有的 musicList
                let musicList = localStorage.getItem("musicList");

                // 将获取到的字符串转换为数组
                if (musicList) {
                    musicList = JSON.parse(musicList);
                } else {
                    // 如果 musicList 不存在，则初始化为空数组
                    musicList = [];
                }
                localStorage.setItem('playingMusic', JSON.stringify(this.tableData[this.tableData.length - 1]));
                // 遍历 tableData 中的每一首歌
                this.tableData.forEach(song => {
                    // 检查是否存在相同的 song 并删除
                    const index = musicList.findIndex(existingSong => existingSong.id === song.id);
                    if (index !== -1) {
                        // 删除已存在的 song
                        musicList.splice(index, 1);
                    }

                    // 将新的 song 放在数组的最前面
                    musicList.unshift(song);
                });

                // 将更新后的数组转换回 JSON 字符串并保存到 localStorage
                localStorage.setItem("musicList", JSON.stringify(musicList));
            }
        },
        // 跳转到歌曲详情页
        clickMusicName(item) {
            // console.log(item);
            let path = '/songDetails?songId=' + item.id;
            if (path === this.$route.fullPath) {
                return;
            } else {
                // console.log(path);
                this.$router.push({
                    path: '/songDetails',
                    query: {
                        songId: item.id
                    }
                })
                // this.$router.push(path);
            }

        },
        // 跳转到歌手详情页
        clickSingerName(item) {
            let path = '/singerDetails?singerId=' + item.singerId;
            if (path === this.$route.fullPath) {
                return;
            } else {
                // console.log(path);
                this.$router.push({
                    path: '/singerDetails',
                    query: {
                        singerId: item.singerId
                    }
                })
                // this.$router.push(path);
            }
        }, copyText(item) {
            let text = item.name + "——" + item.singerName + "——" + item.introduction;
            if (!navigator.clipboard) {
                this.$message({
                    message: '您的浏览器不支持此功能',
                    type: 'error'
                });
                return;
            }
            navigator.clipboard.writeText(text).then(() => {

                this.$message({
                    message: '复制成功，内容为：' + text,
                    type: 'success'
                });
            }).catch(err => {

                this.$message({
                    message: '复制失败',
                    type: 'error'
                });
            });
        },
        downUrl(url) {
            if (url.includes('http')) {
                location.href = url;
            } else {
                this.$message({
                    message: '下载失败',
                    type: 'error'
                });
            }
        },
        removeItem(item1) {
            const musicList = JSON.parse(localStorage.getItem('musicList'));

            // 如果musicList存在且不为空，则过滤掉特定id的项
            if (musicList && musicList.length > 0) {
                const updatedMusicList = musicList.filter(item => item.id !== item1.id);

                // 将更新后的数组保存回localStorage
                localStorage.setItem('musicList', JSON.stringify(updatedMusicList));
            }
        },
        addSong2Collect(item) {
            console.log(item);

            let c = {
                songId: item.id,
                userId: this.user.id
            }
            request.post("/collect", c).then(res => {
                if (res.code == '0') {
                    this.$message({
                        message: '收藏成功',
                        type: 'success'
                    });
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        addSongList2Collect() {
            let c = {
                songListId: this.songListId,
                userId: this.user.id
            }
            request.post("/collect", c).then(res => {
                if (res.code == '0') {
                    this.$message({
                        message: '收藏成功',
                        type: 'success'
                    });
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        initMark() {
            let r = {
                songListId: this.songListId,
                consumerId: this.user.id,
                pageNum: 1,
                pageSize: 1,
            }
            request.get("/rankList/search", {
                params: r
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res);
                    if(res.data.total == 0){
                        this.mark = 0;
                    }else{
                        this.mark = res.data.list[0].score;
                    }
                    
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
            // this.mark=5;
            this.markDialog = true;
        },
        submitMark() {
            let m = {
                songListId: this.songListId,
                consumerId: this.user.id,
                score: this.mark,
                
            }
            // console.log(m);

            request.post("/rankList", m).then(res => {
                // console.log(res);

                if (res.code == '0') {
                    this.$message({
                        message: '评价成功',
                        type: 'success'
                    });
                    this.avgScore();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
            this.markDialog = false;
        },
    },

}
</script>
<style scoped>
.detail {
    max-height: 90vh;
    overflow: auto;
    scrollbar-width: none;
    /* 隐藏滚动条 */
    -ms-overflow-style: none;
    /* 隐藏 IE 和 Edge 的滚动条 */
    overflow: -moz-scrollbars-none;
    /* 隐藏 Firefox 的滚动条 */
}

/* 容器样式 */
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 200px;
    background-color: rgb(255, 255, 255);
    font-family: Arial, sans-serif;
}

/* 标签样式 */
label {
    font-size: 1.2em;
    color: #333;
    margin-bottom: 10px;
}

/* 文本域样式 */
textarea {
    width: 100%;
    max-width: 800px;
    height: 100px;
    max-height: 150px;
    padding: 15px;
    font-size: 1em;
    color: #333;
    border: 2px solid #ccc;
    border-radius: 8px;
    background-color: #fff;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
    resize: vertical;
    /* 允许垂直调整大小 */
}

/* 文本域聚焦时的样式 */
textarea:focus {
    border-color: #007BFF;
    box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
    outline: none;
}

/* 占位符样式 */
::placeholder {
    color: #aaa;
    opacity: 1;
}

/* 占位符聚焦时的样式 */
textarea:focus::placeholder {
    color: #ccc;
}

.detail-icon:hover {
    background-color: rgb(176, 176, 176);
    border-radius: 10px;
}

.el-popover {
    backdrop-filter: blur(10px);
}

.play-btn:hover {
    background-color: rgb(206, 206, 206);
    border-radius: 10px;
}

.music-name:hover,
.singer-name:hover {
    color: #d392f8;
}
</style>