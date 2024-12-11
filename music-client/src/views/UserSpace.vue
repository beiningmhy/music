<template>
    <div style="max-height: 89vh;overflow: auto;" class="space">
        <el-backtop target=".space" style="z-index: 10000;" :bottom="120" :visibility-height="10"></el-backtop>
        <div style="height: 480px;">
            <div
                style="width: 80%;height: 450px;background-color: rgba(244, 244, 244,0.5);margin: 0 auto;margin-top: 50px;display: flex;box-shadow:  10px 10px 6px rgba(0, 0, 0, .2);border-radius: 20px;">
                <!-- 用户头像部分 -->
                <div style="width: 150px;display: flex;flex-direction: column;margin-top: 50px;">
                    <el-image style="width: 150px; height: 150px; border-radius: 20% ;"
                        :src="'http://localhost:8080/api/files/' + user.avatar">
                    </el-image>
                    <el-upload class="upload-demo" action="http://localhost:8080/api/files/upload"
                        :on-success="uploadAvatar">
                        <el-button size="small" type="primary"
                            style="margin-left:30px;margin-top: 20px;">点击上传</el-button>
                        <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
                    </el-upload>
                    <div style="margin:0px auto ;margin-top: 20px;">
                        <span style="font-weight: bold;">UID:</span><el-tag>{{ user.uid }}</el-tag>
                    </div>
                    <div style="margin:0px auto ;margin-top: 20px">
                        <span style="font-weight: bold;">用户名:</span>{{ user.username }}
                    </div>
                </div>
                <!-- 用户信息部分 -->
                <el-tabs v-model="activeName" style="margin-left: 20px;width: 80%;margin-top: 20px;">
                    <el-tab-pane label="基本信息" name="first">
                        <div style="max-height: 350px;width: 90%;overflow-y: auto;">
                            <!-- <div style="font-size: 20px;margin-top: 10px;">
                                <div style="display: flex;">
                                    <span style="font-weight: bold;width: 100px;">用户名:</span>
                                    <el-input v-model="user.username" placeholder="请输入内容" :disabled="isDisabled"></el-input>
                                </div>
                            </div>
                            <div style="font-size: 20px;margin-top: 10px;">
                                <div style="display: flex;">
                                    <span style="font-weight: bold;width: 100px;">用户名:</span>
                                    <el-input v-model="user.username" placeholder="请输入内容" :disabled="isDisabled"></el-input>
                                </div>
                            </div>
                            <div style="font-size: 20px;margin-top: 10px;">
                                <div style="display: flex;">
                                    <span style="font-weight: bold;width: 100px;">用户名:</span>
                                    <el-input v-model="user.username" placeholder="请输入内容" :disabled="isDisabled"></el-input>
                                </div>
                            </div> -->
                            <el-form>

                                <el-form-item label="用户名" label-width="20%" aria-required="true">
                                    <el-input v-model="form.username" autocomplete="off" style="width: 90%"
                                        :disabled="isDisabled || form.singerType == '1'"></el-input>
                                </el-form-item>
                                <el-form-item label="角色" label-width="20%">
                                    <el-select v-model="form.singerType" placeholder="请选择" style="width: 90%" clearable
                                        disabled>
                                        <el-option label="普通用户" value="0"></el-option>
                                        <el-option label="歌手" value="1"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="性别" label-width="20%">
                                    <el-select v-model="form.sex" placeholder="请选择" style="width: 90%" clearable
                                        :disabled="isDisabled || form.singerType == '1'">
                                        <el-option label="男" value="1"></el-option>
                                        <el-option label="女" value="0"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="手机号" label-width="20%" aria-required="true">
                                    <el-input v-model="form.phone" autocomplete="off" style="width: 90%"
                                        :disabled="isDisabled"></el-input>
                                </el-form-item>
                                <el-form-item label="邮箱" label-width="20%" aria-required="true">
                                    <el-input v-model="form.email" autocomplete="off" style="width: 90%"
                                        :disabled="isDisabled"></el-input>
                                </el-form-item>
                                <el-form-item label="出生日期" label-width="20%" aria-required="true">
                                    <el-date-picker v-model="form.birth" type="date" format="yyyy-MM-dd"
                                        value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 90%"
                                        :disabled="isDisabled || form.singerType == '1'"></el-date-picker>
                                </el-form-item>
                                <el-form-item label="介绍" label-width="20%" aria-required="true">
                                    <el-input type="textarea" rows="3" v-model="form.introduction" autocomplete="off"
                                        style="width: 90%" :disabled="isDisabled || form.singerType == '1'"></el-input>
                                </el-form-item>
                                <el-form-item label="地址" label-width="20%" aria-required="true">
                                    <el-tag autocomplete="off" style="width: 90%" size="medium">{{ form.address
                                        }}</el-tag>
                                    <el-cascader v-model="value" :options="cascaderData"
                                        :props="{ expandTrigger: 'hover' }" @change="handleChange"
                                        style="width: 90%;margin-top: 5px;"
                                        :disabled="isDisabled || form.singerType == '1'"></el-cascader>
                                </el-form-item>

                                <el-form-item label="积分" label-width="20%" aria-required="true">
                                    <el-input v-model="form.point" autocomplete="off" style="width: 90%"
                                        disabled></el-input>
                                </el-form-item>

                            </el-form>
                            <div style="margin-top: 30px;margin-left: 30%;">
                                <el-button v-if="isDisabled" type="primary" @click="edit"
                                    style="width: 100px;">修改</el-button>
                                <el-button v-if="!isDisabled" type="success" @click="submit"
                                    style="width: 100px;">提交</el-button>
                                <el-button v-if="!isDisabled" type="warning" @click="cancel"
                                    style="width: 100px;">取消</el-button>
                            </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="安全设置" name="second">
                        <div>
                            <el-form style="margin-left: -35%;">
                                <el-form-item label="请输入原密码" label-width="40%" aria-required="true">
                                    <el-input v-model="form1.oldPw" autocomplete="off" style="width: 90%"></el-input>
                                </el-form-item>
                                <el-form-item label="请输入新密码" label-width="40%" aria-required="true">
                                    <el-input v-model="form1.newPw" autocomplete="off" style="width: 90%"></el-input>
                                </el-form-item>
                                <el-form-item label="请确认新密码" label-width="40%" aria-required="true">
                                    <el-input v-model="form1.confirmPw" autocomplete="off"
                                        style="width: 90%"></el-input>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" style="text-align: center;">
                                <el-button type="primary" @click="submit1()">确 定 修 改</el-button>
                            </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="创作者中心" name="third">
                        <div style="max-height: 350px;width: 90%;overflow-y: auto;height: 350px;"
                            v-if="user.singerType == 0">
                            <div class="container">
                                <h1>音乐网站用户守则须知</h1>
                                <div class="section">
                                    <h2>引言</h2>
                                    <p>在数字化时代，音乐网站成为了音乐爱好者和创作者的重要聚集地。它们不仅为听众提供了一个便捷的音乐获取渠道，也为音乐创作者提供了展示才华的平台。然而，为了维护一个健康、有序的网络环境，确保所有用户的权益得到保护，音乐网站制定了一系列的用户守则。本守则旨在为那些希望从普通用户转变为歌手歌曲上传者的音乐爱好者提供详尽的指导和须知。
                                    </p>
                                </div>
                                <div class="section">
                                    <h2>转变成歌手歌曲上传者之前的须知</h2>
                                    <ol>
                                        <li><strong>了解平台政策：</strong>在决定成为歌曲上传者之前，用户首先需要对音乐网站的服务条款、版权政策、社区指南和上传指南有一个全面的了解。
                                        </li>
                                        <li><strong>注册和验证：</strong>用户需要在音乐网站上注册一个账号，并根据网站的要求提供必要的个人信息。</li>
                                        <!-- 其他须知项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>成为歌曲上传者之后的守则</h2>
                                    <ol>
                                        <li><strong>持续遵守平台政策：</strong>成为上传者后，用户应持续关注并遵守音乐网站的政策更新。</li>
                                        <li><strong>版权声明：</strong>用户在上传音乐作品时，应明确声明作品的版权归属。</li>
                                        <!-- 其他守则项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>版权和法律问题</h2>
                                    <ol>
                                        <li><strong>版权教育：</strong>音乐网站应提供版权教育资源，帮助用户了解版权的基本概念。</li>
                                        <li><strong>版权检测技术：</strong>音乐网站应使用先进的版权检测技术，如音频指纹技术。</li>
                                        <!-- 其他法律问题项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>社区建设和用户关系</h2>
                                    <ol>
                                        <li><strong>社区活动：</strong>音乐网站应定期举办社区活动，如在线音乐会、音乐比赛等。</li>
                                        <li><strong>用户支持：</strong>音乐网站应提供用户支持服务，帮助用户解决在上传和推广过程中遇到的问题。</li>
                                        <!-- 其他社区和用户关系项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>技术和创新</h2>
                                    <ol>
                                        <li><strong>技术支持：</strong>音乐网站应提供技术支持，帮助用户解决在音乐制作和上传过程中遇到的技术问题。</li>
                                        <li><strong>创新工具：</strong>音乐网站可以开发创新工具，如在线音乐制作软件、音频编辑工具等。</li>
                                        <!-- 其他技术和创新项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>结语</h2>
                                    <p>音乐网站不仅是一个音乐分享的平台，更是一个音乐创作和交流的社区。通过遵守上述守则，用户可以确保自己的权益得到保护，同时也为整个社区的繁荣做出贡献。让我们共同努力，创造一个健康、积极、充满创意的音乐环境。
                                    </p>
                                </div>
                            </div>
                            <div style="text-align: center;margin-top: 10px">
                                <el-checkbox v-model="read">我以阅读并同意以上内容</el-checkbox>
                            </div>
                            <div style="text-align: center;">
                                <el-button type="success" @click="next" :disabled="!read">下一步</el-button>
                            </div>

                        </div>
                        <div v-else-if="user.singerType == '1'" style="text-align: left;width: 90%;">
                            <el-statistic group-separator="," :precision="0" :value="songsCount"
                                title="发表歌曲数"></el-statistic>
                            <div style="display: flex;margin-top: 80px;">
                                <div style="display: flex;flex-direction: column;width: 25%;text-align: center;"
                                    @click="goToSingerCenter">
                                    <div style="width: 80;margin: 0 auto;">
                                        <svg t="1733799054761" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                            xmlns="http://www.w3.org/2000/svg" p-id="6429" width="80" height="80">
                                            <path
                                                d="M516.257 293.63c0-122.447-97.891-221.705-218.647-221.705-120.756 0-218.647 99.259-218.647 221.705 0 70.809 32.859 133.718 83.828 174.307h-0.287l353.753 252.299 353.588-252.15h-0.314c51.077-40.581 84.02-103.557 84.02-174.455 0-122.447-97.891-221.705-218.647-221.705-120.755-0.002-218.647 99.257-218.647 221.704"
                                                fill="#F7A09F" p-id="6430"></path>
                                            <path
                                                d="M440.673 322.883l413.9-46.73v512.39c-0.02 0.2-0.02 0.41-0.02 0.61 0 22.76-9.17 45.77-25.82 64.8-18.13 20.73-43.53 35.29-73.46 42.12-32.02 7.31-64.11 4.95-90.34-6.65-25.1-11.1-41.69-29.36-46.73-51.43-9-39.42 21.78-83.12 73.19-103.91 0.06-0.02 0.12-0.05 0.18-0.07 1.18-0.47 2.37-0.93 3.66-1.41l99.66-38.47c4.37-1.69 7.25-5.89 7.25-10.58v-286.32a11.331 11.331 0 0 0-12.72-11.25l-281.62 34.66c-5.69 0.7-9.96 5.52-9.96 11.25v394.25c-0.01 0.29-0.01 0.57-0.01 0.85 1.11 43.37-41.82 86.07-99.86 99.32-32.02 7.31-64.11 4.94-90.34-6.66-25.1-11.1-41.69-29.36-46.73-51.42-9.18-40.21 22.49-84.27 75.24-104.73 0.04-0.02 0.1-0.04 0.14-0.05 5.39-2.08 61.78-17.48 96.06-26.71 5.72-1.54 9.25-7.21 8.21-12.96-1.5-14.75-0.77-243.67 0.12-400.9z"
                                                fill="#B8D6C8" p-id="6431"></path>
                                            <path
                                                d="M873.463 255.014a11.359 11.359 0 0 0-8.83-2.81l-436.51 49.28c-5.71 0.64-10.04 5.46-10.07 11.2-0.01 1.01-0.59 102.1-0.88 203.04-0.39 135.44-0.03 183.62 0.45 201.5-24.94 6.74-82.62 22.4-89.47 25.06-0.05 0.02-0.1 0.03-0.14 0.05-29.94 11.61-55.41 30.71-71.72 53.78-17.12 24.2-23.33 51.61-17.5 77.16 6.67 29.22 27.86 53.06 59.67 67.12 19.12 8.46 40.76 12.75 63.24 12.75 13.59 0 27.48-1.57 41.32-4.72 33.12-7.56 63.16-23.86 84.6-45.89 21.72-22.31 33.39-49.1 32.9-75.51v-261.32l258.95-31.16v141.23l-92.32 35.64c-1.33 0.5-2.67 1.01-3.99 1.54l-0.11 0.04c-0.16 0.07-0.32 0.13-0.48 0.2-29.14 11.85-53.85 30.88-69.62 53.6-16.62 23.97-22.61 51.05-16.86 76.25 6.67 29.22 27.86 53.05 59.67 67.12 30.67 13.56 67.81 16.41 104.56 8.02 34.61-7.9 64.17-24.95 85.48-49.3 20.11-22.98 31.25-51.04 31.42-79.07 0.02-0.22 0.02-0.43 0.02-0.65v-525.7c0-3.231-1.37-6.301-3.78-8.45z m-352.94 287.839v-100.93l258.95-31.87v101.64l-258.95 31.16z m334.05 245.69c-0.02 0.2-0.02 0.41-0.02 0.61 0 22.76-9.17 45.77-25.82 64.8-18.13 20.73-43.53 35.29-73.46 42.12-32.02 7.31-64.11 4.95-90.34-6.65-25.1-11.1-41.69-29.36-46.73-51.43-9-39.42 21.78-83.12 73.19-103.91 0.06-0.02 0.12-0.05 0.18-0.07 1.18-0.47 2.37-0.93 3.66-1.41l99.66-38.47c4.37-1.69 7.25-5.89 7.25-10.58v-286.32a11.331 11.331 0 0 0-12.72-11.25l-281.62 34.66c-5.69 0.7-9.96 5.52-9.96 11.25v394.25c-0.01 0.29-0.01 0.57-0.01 0.85 1.11 43.37-41.82 86.07-99.86 99.32-32.02 7.31-64.11 4.94-90.34-6.66-25.1-11.1-41.69-29.36-46.73-51.42-9.18-40.21 22.49-84.27 75.24-104.73 0.04-0.02 0.1-0.04 0.14-0.05 5.39-2.08 61.78-17.48 96.06-26.71 5.72-1.54 9.25-7.21 8.21-12.96-1.5-14.75-0.77-243.67 0.12-400.9l413.9-46.73v512.39zM379.013 150.323c-1.06-5.53-6.03-9.44-11.67-9.18l-124.9 5.89c-4.66 0.22-8.71 3.27-10.21 7.69l-49.75 147.06c-10.85-3.33-22.64-4.59-34.68-3.66-19.81 1.53-38.11 8.88-51.51 20.69-14.31 12.62-21.48 28.77-20.19 45.48 1.29 16.72 10.86 31.58 26.94 41.84 13.11 8.37 29.37 12.83 46.44 12.83 2.53 0 5.08-0.1 7.64-0.3 34.01-2.63 62.74-22.85 70.03-49.22l50.83-150.67 22.09-2.15c32.15-1.17 55.12-10.11 68.25-26.57 14.6-18.31 10.86-38.86 10.69-39.73z m-28.419 25.59c-8.74 10.95-26.6 17.19-51.66 18.06-0.24 0-0.48 0.02-0.71 0.04l-29.74 2.9c-4.45 0.44-8.22 3.44-9.65 7.66l-53.27 157.94c-0.08 0.22-0.15 0.45-0.21 0.68-4.6 17.16-25.62 30.98-49.98 32.86-14.94 1.16-29.19-2.05-40.13-9.04-9.93-6.33-15.8-15.021-16.53-24.46-0.73-9.45 3.73-18.94 12.57-26.72 9.74-8.59 23.33-13.95 38.27-15.1 1.92-0.15 3.84-0.23 5.75-0.23 10.44 0 20.62 2.2 29.15 6.35 2.92 1.43 6.33 1.53 9.33 0.27a11.3 11.3 0 0 0 6.37-6.82l51.08-150.98 104.9-4.95c-0.821 3.55-2.441 7.65-5.54 11.54z"
                                                fill="#3D464B" p-id="6432"></path>
                                            <path
                                                d="M356.134 164.373c-0.82 3.55-2.44 7.65-5.54 11.54-8.74 10.95-26.6 17.19-51.66 18.06-0.24 0-0.48 0.02-0.71 0.04l-29.74 2.9c-4.45 0.44-8.22 3.44-9.65 7.66l-53.27 157.94c-0.08 0.22-0.15 0.45-0.21 0.68-4.6 17.16-25.62 30.98-49.98 32.86-14.94 1.16-29.19-2.05-40.13-9.04-9.93-6.33-15.8-15.021-16.53-24.46-0.73-9.45 3.73-18.94 12.57-26.72 9.74-8.59 23.33-13.95 38.27-15.1 1.92-0.15 3.84-0.23 5.75-0.23 10.44 0 20.62 2.2 29.15 6.35 2.92 1.43 6.33 1.53 9.33 0.27a11.3 11.3 0 0 0 6.37-6.82l51.08-150.98 104.9-4.95z"
                                                fill="#B8D6C8" p-id="6433"></path>
                                            <path
                                                d="M222.803 575.133c-27.16 0-49.25 22.09-49.25 49.24 0 27.16 22.09 49.25 49.25 49.25 27.15 0 49.24-22.09 49.24-49.25 0-27.149-22.09-49.24-49.24-49.24z m0 75.81c-14.65 0-26.57-11.92-26.57-26.57s11.92-26.56 26.57-26.56 26.57 11.91 26.57 26.56-11.92 26.57-26.57 26.57z"
                                                fill="#3D464B" p-id="6434"></path>
                                            <path
                                                d="M249.373 624.373c0 14.65-11.92 26.57-26.57 26.57s-26.57-11.92-26.57-26.57 11.92-26.56 26.57-26.56 26.57 11.911 26.57 26.56z"
                                                fill="#B8D6C8" p-id="6435"></path>
                                            <path
                                                d="M162.283 789.813c-5.95-23.42-29.84-37.64-53.27-31.69-23.42 5.94-37.64 29.84-31.7 53.26 5.03 19.83 22.91 33.06 42.49 33.06 3.56 0 7.17-0.44 10.78-1.36 19.82-5.03 33.05-22.92 33.05-42.5a43.91 43.91 0 0 0-1.35-10.77z m-24.31 21.61c-2.89 4.85-7.5 8.29-12.97 9.68-11.31 2.87-22.84-3.99-25.71-15.3-2.87-11.3 3.99-22.83 15.3-25.7 1.74-0.45 3.479-0.66 5.2-0.66 9.45 0 18.08 6.39 20.51 15.96 1.39 5.47 0.56 11.16-2.33 16.02z"
                                                fill="#3D464B" p-id="6436"></path>
                                            <path
                                                d="M137.973 811.423c-2.89 4.85-7.5 8.29-12.97 9.68-11.31 2.87-22.84-3.99-25.71-15.3-2.87-11.3 3.99-22.83 15.3-25.7 1.74-0.45 3.479-0.66 5.2-0.66 9.45 0 18.08 6.39 20.51 15.96 1.39 5.47 0.56 11.16-2.33 16.02z"
                                                fill="#F7A09F" p-id="6437"></path>
                                        </svg>
                                    </div>

                                    歌曲中心
                                </div>
                                <div style="display: flex;flex-direction: column;width: 25%;text-align: center;"
                                    @click="goToSongListCenter">
                                    <div style="width: 80;margin: 0 auto;">
                                        <svg t="1733800197937" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                            xmlns="http://www.w3.org/2000/svg" p-id="7453" width="80" height="80">
                                            <path
                                                d="M836 938.7H380.7c-1.3 0-2.3-1-2.3-2.3V545.1c0-1.3 1-2.3 2.3-2.3H836c1.3 0 2.3 1 2.3 2.3v391.3c0 1.2-1.1 2.3-2.3 2.3z"
                                                fill="#F4C708" p-id="7454"></path>
                                            <path
                                                d="M763 960H261c-53.3 0-96.6-43.4-96.6-96.6v-502c0-53.3 43.4-96.6 96.6-96.6h502c53.3 0 96.6 43.4 96.6 96.6v502c0 53.2-43.3 96.6-96.6 96.6zM261 307.4c-29.8 0-54 24.2-54 54v502c0 29.8 24.2 54 54 54h502c29.8 0 54-24.2 54-54v-502c0-29.8-24.2-54-54-54H261zM737.9 207H286.1c-11.8 0-21.3-9.5-21.3-21.3s9.5-21.3 21.3-21.3h451.8c11.8 0 21.3 9.5 21.3 21.3s-9.5 21.3-21.3 21.3zM662.6 106.7H361.4c-11.8 0-21.3-9.5-21.3-21.3S349.6 64 361.4 64h301.2c11.8 0 21.3 9.5 21.3 21.3s-9.5 21.4-21.3 21.4z"
                                                fill="#111111" p-id="7455"></path>
                                            <path
                                                d="M574.2 724.5c-10.2 0-19.9-2.5-28.5-7.4-16.9-9.8-27.7-28.1-29.7-50.5-1.7-19.8 3.5-41.4 14.7-60.8s27.3-34.7 45.4-43.1c20.3-9.5 41.6-9.3 58.5 0.4 9.1 5.3 16.4 12.9 21.7 22.8 1.6 3.1 2.5 6.5 2.5 10v54.4c0 2.6-0.5 5.2-1.4 7.6-2.1 5.5-4.6 11-7.8 16.4-11.2 19.4-27.3 34.7-45.4 43.1-9.9 4.8-20.2 7.1-30 7.1z m32-126.2c-4.6 0-9.1 1.7-12 3-10 4.7-19.7 14.1-26.4 25.8-6.8 11.7-10.1 24.7-9.1 35.8 0.4 4.9 2.1 13.5 8.5 17.2 6.4 3.7 14.8 0.8 19.2-1.3 10-4.7 19.7-14.1 26.4-25.8 1.3-2.3 2.5-4.6 3.5-6.9v-43.8c-0.9-0.9-1.8-1.7-2.8-2.3-2.4-1.2-4.9-1.7-7.3-1.7z"
                                                fill="#111111" p-id="7456"></path>
                                            <path
                                                d="M386.5 709c-11.8 0-21.3-9.5-21.3-21.3V386.5c0-11.8 9.5-21.3 21.3-21.3h251c11.8 0 21.3 9.5 21.3 21.3v251c0 11.8-9.5 21.3-21.3 21.3s-21.3-9.5-21.3-21.3V407.8H407.8v279.9c0 11.8-9.5 21.3-21.3 21.3z"
                                                fill="#111111" p-id="7457"></path>
                                            <path
                                                d="M323.2 824.9c-10.2 0-19.9-2.5-28.5-7.4-33.3-19.3-39.9-68.1-15-111.3s70.6-61.9 103.9-42.7c9.1 5.3 16.4 12.9 21.7 22.8 1.6 3.1 2.5 6.5 2.5 10v54.4c0 2.6-0.5 5.2-1.4 7.6-2.1 5.5-4.6 11-7.8 16.4-11.2 19.4-27.3 34.7-45.4 43.1-9.9 4.8-20.1 7.1-30 7.1z m32-126.2c-11.5 0-27.7 10.3-38.4 28.8-13.2 22.9-11.3 46.9-0.6 53 6.4 3.7 14.8 0.8 19.2-1.3 10-4.7 19.7-14.1 26.4-25.8 1.3-2.3 2.5-4.6 3.5-6.9v-43.8c-0.9-0.9-1.8-1.7-2.8-2.3-2.2-1.1-4.6-1.7-7.3-1.7z"
                                                fill="#111111" p-id="7458"></path>
                                        </svg>
                                    </div>

                                    歌单中心
                                </div>
                                <div style="display: flex;flex-direction: column;width: 25%;text-align: center;"
                                    @click="goToStatementCenter">
                                    <div style="width: 80;margin: 0 auto;">
                                        <svg t="1733826577545" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                            xmlns="http://www.w3.org/2000/svg" p-id="4295" width="80" height="80">
                                            <path d="M519.68 718.1312h20.48v76.8h-20.48z" fill="#FDD55B" p-id="4296">
                                            </path>
                                            <path d="M206.5408 223.3344h631.1936v61.44H206.5408z" fill="#67DCD1"
                                                p-id="4297"></path>
                                            <path
                                                d="M834.56 295.0144h-78.0288v-20.48h78.0288v-40.96h-614.4v40.96h490.2912v20.48H220.16a19.7632 19.7632 0 0 1-20.48-18.944v-43.9296a19.8656 19.8656 0 0 1 20.48-19.0464h614.4a19.8656 19.8656 0 0 1 20.48 19.0464V276.48a19.7632 19.7632 0 0 1-20.48 18.5344z"
                                                fill="#3F44EC" p-id="4298"></path>
                                            <path
                                                d="M231.0144 288.0512h20.48v430.08h-20.48zM804.4544 288.0512h20.48v430.08h-20.48z"
                                                fill="#3F44EC" p-id="4299"></path>
                                            <path
                                                d="M199.68 705.7408h655.36v20.48h-655.36zM312.32 623.8208h430.08v20.48h-430.08z"
                                                fill="#3F44EC" p-id="4300"></path>
                                            <path
                                                d="M517.12 634.0608v-286.72h20.48v286.72zM435.2 634.0608v-245.76h20.48v245.76zM599.04 634.0608v-245.76h20.48v245.76M680.96 634.0608v-184.32h20.48v184.32M353.28 634.0608v-184.32h20.48v184.32M312.7296 368.2304v-20.48h19.6608v20.48M691.2 368.2304v-20.48h51.2v20.48"
                                                fill="#3F44EC" p-id="4301"></path>
                                            <path
                                                d="M529.3056 874.2912a40.96 40.96 0 1 1 40.96-40.96 40.96 40.96 0 0 1-40.96 40.96z m0-61.44a20.48 20.48 0 1 0 20.48 20.48 20.48 20.48 0 0 0-20.48-20.48z"
                                                fill="#FDD55B" p-id="4302"></path>
                                        </svg>
                                    </div>

                                    统计中心
                                </div>
                            </div>

                        </div>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>

    </div>

</template>
<script>
import request from '@/utils/request'
export default {
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : '',
            activeName: 'first',
            isDisabled: true,
            form: '',
            cascaderData: [],
            value: '',
            form1: {
                oldPw: '',
                newPw: '',
                confirmPw: '',
                userId: ''

            },
            read: false,
            songsCount: 0,
        }
    },
    created() {

    },

    mounted() {
        this.initCascader();
        this.form = JSON.parse(JSON.stringify(this.user));
        this.initSongsCount();
    },
    methods: {
        initCascader() {
            request.get("/address/map").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.cascaderData = res.data;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        async handleChange(value) {
            let address = '';
            // console.log(value);
            if (value != null) {
                for (let i = 0; i < value.length; i++) {
                    const element = value[i];
                    // console.log(element);
                    await request.get('/address/' + element).then(res => {
                        if (res.code === '0') {
                            // console.log(res.data);
                            address = address + res.data + '/'
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                }
                // console.log(address);
                this.form.address = address;
            }

        },
        async initSongsCount() {
            if (this.user.singerType == '1') {
                let p = {
                    name: this.user.username,
                    pageNum: 1,
                    pageSize: 1
                }
                let singerId;
                await request.get('/singer/search', {
                    params: p
                }).then(res => {
                    if (res.code === '0') {
                        // console.log(res.data);
                        singerId = res.data.list[0].id;
                    } else {
                        this.$message.error(res.msg);
                        return;
                    }
                })
                let user = JSON.parse(JSON.stringify(this.user));
                user.singerId = singerId;
                localStorage.setItem("user", JSON.stringify(user));


                let p2 = {
                    pageNum: 1,
                    pageSize: 1,
                    singerId: singerId
                }
                // console.log(p2);

                await request.get('/song/search', {
                    params: p2
                }).then(res => {
                    if (res.code === '0') {
                        // console.log(res.data);
                        this.songsCount = res.data.total;
                    } else {
                        this.$message.error(res.msg);
                        return;
                    }
                })
            }

        },
        edit() {
            this.isDisabled = false;
        },
        submit() {
            request.post("/consumer", this.form).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.user = this.form;
                    console.log(this.user);
                    this.isDisabled = true;
                    localStorage.setItem("user", JSON.stringify(this.user));

                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        cancel() {
            this.form = JSON.parse(JSON.stringify(this.user));
            this.value = [];
            this.isDisabled = true;
        },
        submit1() {
            // console.log(this.form1);
            if (this.form1.oldPw == '' || this.form1.newPw == '' || this.form1.confirmPw == '') {
                this.$message({
                    message: '请输入完整信息',
                    type: 'warning'
                });
                return;
            }
            if (this.form1.oldPw == this.form1.newPw) {
                this.$message({
                    message: '新密码与原密码相同',
                    type: 'warning'
                });
                return;
            }
            if (this.form1.newPw != this.form1.confirmPw) {
                this.$message({
                    message: '两次输入的密码不一致',
                    type: 'warning'
                });
                return;
            }
            this.form1.userId = this.user.id;
            request.post('/consumer/changePassword', this.form1).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '修改成功，请重新登录！！！',
                        type: 'success'
                    });
                    this.form1 = {};
                    localStorage.removeItem("user");
                    this.$router.push('/login');
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })

        },
        uploadAvatar(res) {
            // console.log(res);
            this.user.avatar = res.data;
            localStorage.setItem("user", JSON.stringify(this.user));
            request.post('/consumer', this.user).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '上传成功',
                        type: 'success'
                    });
                    window.location.reload();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })


        },
        next() {
            // this.$message({
            //     message: '功能尚未开放，敬请期待',
            //     type: 'warning'
            // })
            this.$router.push('/agreement');
        },
        goToSingerCenter() {
            this.$router.push('/songCenter');
        },
        goToSongListCenter() {
            this.$router.push('/songListCenter');
        },
        goToStatementCenter(){
            this.$router.push('/statementCenter');
        },  
    }


}
</script>
<style scoped>
.container {
    max-width: 800px;
    margin: auto;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1,
h2,
h3 {
    color: #333;
}

h1 {
    text-align: center;
}

.section {
    margin-bottom: 20px;
}

.section:last-child {
    margin-bottom: 0;
}

p {
    margin-bottom: 15px;
}

ul {
    padding-left: 20px;
}

ul li {
    margin-bottom: 10px;
}

ul li:last-child {
    margin-bottom: 0;
}
</style>