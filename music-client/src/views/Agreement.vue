<template>
    <div style="max-height: 89vh;overflow: auto;" class="agreement">
        <el-backtop target=".agreement" :visibility-height="10" :bottom="120"></el-backtop>
        <div>
            <div style="text-align: center;">
                <h1>创作者认证中心</h1>
            </div>
            <el-divider></el-divider>

            <div style="width: 90%;margin: 0 auto;">
                <el-steps :active="active" finish-status="success">
                    <el-step title="步骤 1"> </el-step>
                    <el-step title="步骤 2"> </el-step>
                    <el-step title="步骤 3"> </el-step>
                </el-steps>
                <!-- <el-button style="margin-top: 12px;" @click="next">下一步</el-button> -->

                <div>
                    <div v-if="active == 0" style="text-align: center;">
                        <h2>认证成为音乐创作者，需要支付10000用户积分</h2>
                        <h3>当前用户积分为：{{ user.point }}</h3>
                        <h3 v-if="user.point >= 10000">满足当前要求可以进行下一步</h3>
                        <h3 v-else>不满足当前要求，请充值后再进行下一步</h3>
                        <el-button style="margin-top: 12px;" @click="next"
                            :disabled="user.point < 10000">下一步</el-button>

                    </div>
                    <div v-else-if="active == 1" style="text-align: center;">
                        <div style="display: flex;flex-direction: column;">
                            <div style="margin-top: 10px;">
                                歌手名：<el-tag type="success">{{ user.username }}</el-tag>
                            </div>
                            <div style="margin-top: 10px;">
                                性别：<el-tag v-if="user.sex === '0'" type="danger">女</el-tag>
                                <el-tag v-else-if="user.sex === '1'" type="success">男</el-tag>
                                <el-tag v-else type="danger">未知</el-tag>
                            </div>
                            <div style="margin-top: 10px;">
                                头像: <el-image style="width: 70px; height: 70px; border-radius: 50%"
                                    :src="'http://localhost:8080/api/files/' + user.avatar"
                                    :preview-src-list="['http://localhost:8080/api/files/' + user.avatar]">
                                </el-image>
                            </div>
                            <div style="margin-top: 10px;">
                                生日：<el-tag type="success">{{ user.birth }}</el-tag>
                            </div>
                            <div style="margin-top: 10px;">
                                地址：<el-tag type="success">{{ user.address }}</el-tag>
                            </div>
                            <div style="margin-top: 10px;">
                                介绍：<el-tag type="success">{{ user.introduction }}</el-tag>
                            </div>
                            <div style="margin-top: 10px;">
                                请再次确认以上内容，确认无误后点击下一步
                                <br>
                                如果有问题请返回用户空间修正
                            </div>

                        </div>

                        <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
                    </div>
                    <div v-else-if="active == 2" style="max-height: 350px;overflow-y: auto;">
                        <div>
                            <h1>音乐网站用户守则须知</h1>

                            <div class="section">
                                <h2>引言</h2>
                                <p>在数字化时代，音乐网站成为了音乐爱好者和创作者的重要聚集地。它们不仅为听众提供了一个便捷的音乐获取渠道，也为音乐创作者提供了展示才华的平台。然而，为了维护一个健康、有序的网络环境，确保所有用户的权益得到保护，音乐网站制定了一系列的用户守则。本守则旨在为那些希望从普通用户转变为歌手歌曲上传者的音乐爱好者提供详尽的指导和须知。
                                </p>
                            </div>

                            <div class="section">
                                <h2>转变成歌手歌曲上传者之前的须知</h2>
                                <ol>
                                    <li><strong>了解平台政策：</strong>
                                        在决定成为歌曲上传者之前，用户首先需要对音乐网站的服务条款、版权政策、社区指南和上传指南有一个全面的了解。这些政策通常可以在网站的“帮助中心”或“关于我们”部分找到。用户应仔细阅读这些文件，确保自己对平台的运作方式、权利和义务有清晰的认识。
                                    </li>
                                    <li><strong>注册和验证：</strong>
                                        用户需要在音乐网站上注册一个账号，并根据网站的要求提供必要的个人信息，如姓名、电子邮件地址、电话号码等。某些网站可能还需要用户进行身份验证，以确保账号的真实性和安全性。这可能包括上传身份证明文件、进行视频验证或通过短信验证等。
                                    </li>
                                    <li><strong>版权意识：</strong>
                                        版权是音乐创作者的重要权益。用户在上传音乐作品之前，必须确保自己拥有作品的完整版权，或者已经获得了必要的授权。这包括音乐、歌词、采样、封面艺术等所有组成部分。如果作品包含采样或翻唱，用户应确保已经获得了原版权所有者的许可。
                                    </li>
                                    <li><strong>内容质量：</strong>
                                        音乐网站鼓励用户上传高质量的音乐作品。用户应确保上传的音乐文件音质清晰，混音和制作水平达到专业标准。此外，作品的封面艺术和描述也应具有吸引力，以提高作品的曝光率和受欢迎程度。
                                    </li>
                                    <li><strong>遵守法律法规：</strong>
                                        用户上传的内容必须遵守所有适用的国家和地区法律法规。这包括但不限于版权法、商标法、隐私法等。用户应避免上传任何可能引起法律争议的内容，如色情、暴力、种族歧视、诽谤等。
                                    </li>
                                    <li><strong>社区参与：</strong>在成为上传者之前，用户应积极参与音乐网站的社区活动，如评论、点赞、分享和讨论。这不仅有助于用户了解社区的文化和用户偏好，也有助于建立自己的社交网络，为将来的作品推广打下基础。
                                    </li>
                                </ol>
                            </div>

                            <div class="section">
                                <h2>成为歌曲上传者之后的守则</h2>
                                <ol>
                                    <li><strong>持续遵守平台政策：</strong>成为上传者后，用户应持续关注并遵守音乐网站的政策更新。平台可能会根据法律法规的变化或社区反馈对政策进行调整。用户应及时了解这些变化，并确保自己的行为始终符合最新的政策要求。
                                    </li>
                                    <li><strong>版权声明：</strong>用户在上传音乐作品时，应明确声明作品的版权归属。这包括在作品描述中注明版权信息，如“©2024
                                        音乐人姓名”。此外，用户还应确保作品的版权信息在音乐网站的版权数据库中正确注册。</li>
                                    <li><strong>作品管理：</strong>用户应定期检查自己的作品，确保所有信息都是最新的。这包括更新作品的描述、封面艺术、歌词等。如果作品需要下架或修改，用户应及时进行操作，以避免可能的版权纠纷或用户投诉。
                                    </li>
                                    <li><strong>用户反馈：</strong>用户应积极回应听众的反馈，包括评论、建议和批评。这不仅有助于提高作品的质量，也有助于建立良好的用户关系。用户应以开放的心态接受反馈，并在必要时对作品进行改进。
                                    </li>
                                    <li><strong>版权保护：</strong>用户应采取措施保护自己的作品不被非法复制或分发。这可能包括使用水印、加密技术或数字版权管理（DRM）系统。同时，用户也应关注自己的作品在其他平台上的使用情况，及时处理侵权行为。
                                    </li>
                                    <li><strong>遵守税务和合同义务：</strong>如果用户通过音乐网站获得收入，应遵守相关的税务法规，按时申报和缴纳税款。此外，用户还应履行与音乐网站的合同义务，如分成比例、版权授权期限等。
                                    </li>
                                    <li><strong>维护社区和谐：</strong>用户应积极参与社区建设，避免发布或参与任何可能破坏社区和谐的行为。这包括避免发布恶意评论、参与网络欺凌或散布虚假信息等。
                                    </li>
                                    <li><strong>持续学习和改进：</strong>用户应不断学习音乐制作和市场营销的相关知识，以提高自己的专业水平和市场竞争力。这可能包括参加在线课程、阅读行业报告、参加行业会议等。
                                    </li>
                                    <li><strong>透明度：</strong>用户在上传作品时应保持透明度，如实描述作品内容和创作背景。不得误导听众，如虚假宣传、夸大作品成就等。</li>
                                    <li><strong>紧急情况处理：</strong>用户应知晓并遵守音乐网站的紧急情况处理流程，如版权争议、违规内容举报等。在遇到这些问题时，用户应及时与音乐网站的工作人员联系，按照规定的流程解决问题。
                                    </li>

                                </ol>
                            </div>

                            <div class="section">
                                <h2>版权和法律问题</h2>
                                <ul>
                                    <li><strong>版权教育：</strong> 音乐网站应提供版权教育资源，帮助用户了解版权的基本概念、版权法的基本原则以及如何在创作和上传过程中保护版权。
                                    </li>
                                    <li><strong>版权检测技术：</strong>音乐网站应使用先进的版权检测技术，如音频指纹技术，以自动识别和防止侵权内容的上传。</li>
                                    <li><strong>版权争议解决：</strong>音乐网站应建立一个公正、高效的版权争议解决机制，以便在发生版权纠纷时，能够及时、公正地解决问题。</li>
                                    <li><strong>法律咨询：</strong>音乐网站可以提供法律咨询服务，帮助用户解决在创作和上传过程中遇到的法律问题。</li>

                                </ul>
                            </div>

                            <div class="section">
                                <h2>社区建设和用户关系</h2>
                                <ul>
                                    <li><strong>社区活动：</strong> 音乐网站应定期举办社区活动，如在线音乐会、音乐比赛、创作挑战等，以增强用户的参与感和归属感。</li>
                                    <li><strong>用户支持：</strong>音乐网站应提供用户支持服务，帮助用户解决在上传和推广过程中遇到的问题。</li>
                                    <li><strong>用户反馈机制：</strong>音乐网站应建立一个有效的用户反馈机制，让用户能够及时反馈问题和建议，以便平台不断改进和优化。</li>

                                    <li><strong>用户激励计划：</strong>音乐网站可以设立用户激励计划，如排行榜、奖励制度等，以鼓励用户创作和上传高质量的音乐作品。</li>

                                </ul>
                            </div>

                            <div class="section">
                                <h2>技术和创新</h2>
                                <ul>
                                    <li><strong>技术支持：</strong>音乐网站应提供技术支持，帮助用户解决在音乐制作和上传过程中遇到的技术问题。</li>
                                    <li><strong>创新工具：</strong>音乐网站可以开发创新工具，如在线音乐制作软件、音频编辑工具等，以提高用户的创作效率和作品质量。</li>
                                    <li><strong>数据分析：</strong>音乐网站应提供数据分析服务，帮助用户了解作品的受欢迎程度、听众偏好等信息，以便用户更好地进行市场定位和作品推广。
                                    </li>

                                    <li><strong>安全保障：</strong>音乐网站应采取严格的安全措施，保护用户的个人信息和作品不被非法访问或盗用。</li>

                                </ul>
                            </div>

                            <div class="section">
                                <h2>结语</h2>
                                <p>音乐网站不仅是一个音乐分享的平台，更是一个音乐创作和交流的社区。通过遵守上述守则，用户可以确保自己的权益得到保护，同时也为整个社区的繁荣做出贡献。让我们共同努力，创造一个健康、积极、充满创意的音乐环境。
                                </p>
                            </div>
                        </div>
                        <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
                    </div>
                    <div v-else-if="active == 3">
                        <div style="text-align: center; display: flex;flex-direction: column;">
                            点击完成认证
                            <div>
                                <el-button style="margin-top: 12px;" @click="next">完成认证</el-button>
                            </div>

                        </div>

                    </div>
                </div>
            </div>

        </div>

        <div style="height: 15vh;">

        </div>
    </div>
</template>
<script>
import request from '@/utils/request';

export default {
    data() {
        return {
            active: 0,
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : '',
        }
    },

    methods: {
        next() {
            this.active++;
            if (this.active > 3) {
                this.active--;
                this.submit();

            };
        },
        submit() {
            let singer = {
                name: this.user.username,
                sex: this.user.sex,
                pic: this.user.avatar,
                birth: this.user.birth,
                address: this.user.address,
                introduction: this.user.introduction,
            };
            request.post('/singer', singer).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '提交成功',
                        type: 'success'
                    });
                    let user = this.user;
                    user.point = user.point - 10000;
                    user.singerType='1';
                    request.post('/consumer', user).then(res => {
                        if (res.code === '0') {
                            localStorage.setItem("user", JSON.stringify(user));
                            this.$router.push('/');
                            this.$message({
                                message: '认证成功',
                                type: 'success'
                            });
                        } else {
                            this.$message({
                                message: res.msg,
                                type: 'error'
                            });
                        }
                    })
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })

        },


    },
    mounted() {

    },
    created() {

    }


}
</script>
<style scoped>
body {
    font-family: Arial, sans-serif;
}

h1,
h2,
h3 {
    color: #2c3e50;
}

ul {
    list-style-type: disc;
    margin-left: 20px;
}

li {
    margin-bottom: 10px;
}

.section {
    margin-bottom: 20px;
}
</style>