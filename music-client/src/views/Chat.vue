<template>
    <div style="padding: 10px; margin-bottom: 50px">
        <el-row>
            <el-col :span="5">
                <el-card style="width: 100%; max-height: 300px;height: 300px;overflow-y:auto ; color: #333">
                    <div style="padding-bottom: 10px; border-bottom: 1px solid #ccc">好友列表<span
                            style="font-size: 12px">（点击聊天气泡开始聊天）</span></div>
                    <div style="height: 220px;overflow-y: auto">
                        <div style="" v-for="user in users" :key="user.id">
                            <div style="height: 60px;width: 90%;display: flex;margin-left: 5%;border-bottom: #333 1px solid ;"
                                @click="chat(user)">
                                <div style="">
                                    <el-image
                                        style="width: 50px; height: 50px; border-radius: 10px;margin-top: 5px;margin-left: 5px;"
                                        :src="'http://localhost:8080/api/files/' + user.avatar">
                                    </el-image>
                                </div>
                                <div style="width: 100px;overflow: hidden;">
                                    <div style="margin-left: 10px;margin-top: 5px;">{{ user.username }}</div>
                                </div>
                                <div style="height: 60px;line-height: 60px;">
                                    <el-badge :value="user.num" class="item" v-if="user.num > 0">
                                    </el-badge>
                                </div>

                            </div>



                        </div>
                    </div>

                </el-card>
                <el-card style="width: 100%; min-height: 150px; color: #333;margin-top: 30px;">
                    <div style="padding-bottom: 10px; border-bottom: 1px solid #ccc;display: flex;height: 40px;">
                        <span style="width: 90px;height: 40px;line-height: 40px;overflow: hidden;">
                            添加用户
                        </span>

                        <el-input placeholder="请输入UID" v-model="search" class="input-with-select" clearable
                            style="box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);border-radius: 10px;">
                            <el-button slot="append" icon="el-icon-search" @click="searchUser()"></el-button>
                        </el-input>
                    </div>
                    <div style="height: 80px;overflow-y: auto;">
                        <div style="padding: 10px 0;height: 80px;" v-for="user in addFriends" :key="user.id">
                            <div style="height: 60px;width: 100%;margin-bottom: 10px;display: flex;">
                                <div style="">
                                    <el-image
                                        style="width: 50px; height: 50px; border-radius: 10px;margin-top: 5px;margin-left: 5px;"
                                        :src="'http://localhost:8080/api/files/' + user.avatar">
                                    </el-image>
                                </div>
                                <div style="width: 100px;overflow: hidden;">
                                    <div style="margin-left: 10px;margin-top: 5px;">{{ user.username }}</div>
                                </div>
                                <div style="height: 60px;line-height: 60px;">
                                    <el-button type="primary" size="mini" @click="addFriend(user.id)">添加</el-button>
                                </div>
                            </div>

                        </div>
                    </div>

                </el-card>
            </el-col>
            <el-col :span="18">
                <div style="width: 90%; margin: 0 auto; background-color: white;
                    border-radius: 5px; box-shadow: 0 0 10px #ccc">
                    <div style="text-align: center; line-height: 50px;">
                        music聊天室({{ chatUser.username }})
                    </div>
                    <div style="height: 250px; overflow:auto; border-top: 1px solid #ccc" v-html="content" ref="chat">
                    </div>
                    <div style="height: 200px">
                        <textarea v-model="message.content" style="height: 160px; width: 100%; padding: 20px; border: none; border-top: 1px solid #ccc;
                border-bottom: 1px solid #ccc; outline: none"></textarea>
                        <div style="text-align: right; padding-right: 10px">
                            <el-button type="primary" size="mini" @click="sendMessage()">发送</el-button>
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
        <!-- 消息：<input type="text" v-model="message.content">
        接收者：<input type="text" v-model="message.touser">
        <button @click="sendMessage">发送</button> -->
    </div>
</template>
<script>
import request from '@/utils/request';


export default {
    name: "Im",
    data() {
        return {
            circleUrl: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : '',
            isCollapse: false,
            users: [],
            touser: '',
            chatUser: { id: 0 },
            text: "",
            messages: [],
            content: '',
            message: {
                fromuser: '',
                touser: '',
                content: ''
            },
            search: '',
            addFriends: [],
        }
    },
    created() {
        this.initWebSocket()
    },
    mounted() {
        this.initUsers();

    },
    methods: {
        // send() {
        //     if (!this.chatUser) {
        //         this.$message({ type: 'warning', message: "请选择聊天对象" })
        //         return;
        //     }
        //     if (!this.text) {
        //         this.$message({ type: 'warning', message: "请输入内容" })
        //     } else {
        //         if (typeof (WebSocket) == "undefined") {
        //             console.log("您的浏览器不支持WebSocket");
        //         } else {
        //             console.log("您的浏览器支持WebSocket");
        //             // 组装待发送的消息 json
        //             // {"from": "zhang", "to": "admin", "text": "聊天文本"}
        //             let message = { from: this.user.username, to: this.chatUser, text: this.text }
        //             socket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
        //             this.messages.push({ user: this.user.username, text: this.text })
        //             // 构建消息内容，本人消息
        //             this.createContent(null, this.user.username, this.text)
        //             this.text = '';
        //         }
        //     }
        // },
        initUsers() {
            request.get("/friend/friends/" + this.user.id).then(res => {
                if (res.code == '0') {
                    // console.log(res.data);

                    this.users = res.data;
                } else {
                    this.$message({
                        type: 'error',
                        message: res.msg
                    })
                }
            })
        },
        async searchUser() {
            if (this.search == '') {
                this.$message({
                    type: 'error',
                    message: "请输入UID"
                })
                return;
            }
            let p = {
                uid: this.search,
                pageNum: 1,
                pageSize: 10
            }
            await request.get('/consumer/search', {
                params: p
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.addFriends = res.data.list;

                } else {
                    this.$message({
                        type: 'error',
                        message: res.msg
                    })
                }
            })
        },

        addFriend(id) {
            let p = {
                friend1: this.user.id,
                friend2: id,
            };
            request.post('/friend', p).then(res => {
                if (res.code === '0') {
                    this.$message({
                        type: 'success',
                        message: "添加成功"
                    })
                    this.initUsers();
                } else {
                    this.$message({
                        type: 'error',
                        message: res.msg
                    })
                }
            })
        },
        chat(user) {
            this.content = '';
            this.chatUser = user;
            this.message.touser = user.id;
            let fromUser = this.user.id;
            let toUser = user.id;
            request.get('/imsingle/?fromuser=' + fromUser + "&touser=" + toUser).then(res => {
                if (res.code === '0') {
                    console.log(res.data);
                    this.initUsers();
                    for (let i = 0; i < res.data.length; i++) {
                        if (res.data[i].fromuser != this.user.id) {
                            console.log("111" + res.data[i].fromavatar);

                            this.createContent(res.data[i].fromuser, '', res.data[i].content, res.data[i].fromavatar);
                        } else {
                            console.log("222" + res.data[i].fromavatar);
                            this.createContent('', res.data[i].touser, res.data[i].content, res.data[i].fromavatar);
                        }


                    }

                } else {
                    this.$message({
                        type: 'error',
                        message: res.msg
                    })
                }
            })
        },
        createContent(remoteUser, nowUser, text, avatar) {  // 这个方法是用来将 json的聊天消息数据转换成 html的。
            let html;
            console.log(avatar);

            if (avatar == null) {
                avatar = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png';
            } else {
                avatar = 'http://localhost:8080/api/files/' + avatar;
            }
            // 当前用户消息
            if (nowUser) { // nowUser 表示是否显示当前用户发送的聊天消息，绿色气泡
                html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
                    "  <div class=\"el-col el-col-22\" style=\"text-align: right; padding-right: 10px\">\n" +
                    "    <div class=\"tip left\">" + text + "</div>\n" +
                    "  </div>\n" +
                    "  <div class=\"el-col el-col-2\">\n" +
                    "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
                    "    <img src=\"" + avatar + "\" style=\"object-fit: cover;width:100%;height:100%;object-fit: fill;\">\n" + "<span style='color:#000;'>" + nowUser + "</span>" +
                    "  </span>\n" +
                    "  </div>\n" +
                    "</div>";
            } else if (remoteUser) {   // remoteUser表示远程用户聊天消息，蓝色的气泡
                html = "<div class=\"el-row\" style=\"padding: 5px 0\">\n" +
                    "  <div class=\"el-col el-col-2\" style=\"text-align: right\">\n" +
                    "  <span class=\"el-avatar el-avatar--circle\" style=\"height: 40px; width: 40px; line-height: 40px;\">\n" +
                    "    <img src=\"" + avatar + "\" style=\"object-fit: cover;\">\n" +
                    remoteUser +
                    "  </span>\n" +
                    "  </div>\n" +
                    "  <div class=\"el-col el-col-22\" style=\"text-align: left; padding-left: 10px\">\n" +
                    "    <div class=\"tip right\">" + text + "</div>\n" +
                    "  </div>\n" +
                    "</div>";
            }
            // console.log(html)
            this.content += html;
            setTimeout(() => {
                let container = this.$refs.chat;
                container.scrollTop = container.scrollHeight + 20;
            }, 100);

        },
        initWebSocket() {

            // 检查浏览器是否支持WebSocket
            if (!'WebSocket' in window) return;

            // 初始化WebSocket连接
            this.websocket = new WebSocket("ws://127.0.0.1:8080/imserverSingle/" + this.user.id);

            // 成功建立连接
            this.websocket.onopen = () => {
                // this.websocket.send("成功连接到服务器");
            };

            // 接收到消息
            this.websocket.onmessage = (event) => {
                // console.log(event.data);
                let obj = JSON.parse(event.data);
                if (obj.fromuser == this.chatUser.id) {
                    this.createContent(obj.fromuser, '', obj.content,this.chatUser.avatar)
                }

            };

            // 连接发生错误
            this.websocket.onerror = () => {
                this.$message({
                    message: "WebSocket连接发生错误",
                    type: 'error'
                });
                // alert("WebSocket连接发生错误");
            };

            // 连接关闭
            this.websocket.onclose = () => {
                this.$message({
                    message: "WebSocket连接关闭",
                    type: 'error'
                });
                // alert("WebSocket连接关闭");
            };
        },
        sendMessage() {
            // 发送消息
            // this.websocket.send("发送的消息内容");
            if (this.chatUser.id == 0) {
                this.$message({
                    message: "请选择聊天对象",
                    type: 'error'
                });
                return;
            }
            if (this.message.content == '') {

                this.$message({
                    message: "请输入内容",
                    type: 'error'
                });
                return;
            }
            this.message.fromuser = this.user.id;
            let message = this.message;

            this.createContent('', this.user.username, message.content,this.user.avatar)

            this.websocket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
            this.message.content = '';
        },



    }
}
</script>
<style>
.tip {
    color: white;
    text-align: center;
    border-radius: 10px;
    font-family: sans-serif;
    padding: 10px;
    width: auto;
    display: inline-block !important;
    display: inline;
}

.right {
    background-color: deepskyblue;
}

.left {
    background-color: forestgreen;
}
</style>