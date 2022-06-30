<template>

  <el-card style="width: 500px;margin-top:50px;margin-left: 20%">

    <el-upload

        class="avatar-uploader"
        action="http://localhost:9090/file/upload"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
      <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
    </el-upload>

    <el-form label-width="120px" size="small">
      <el-form-item label="用户名"  ><!--  :label-width="formLabelWidth"-->
        <el-input v-model="form.username" disabled auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称" ><!--  :label-width="formLabelWidth"-->
        <el-input v-model="form.nickname" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" ><!--  :label-width="formLabelWidth"-->
        <el-input v-model="form.email" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址" ><!--  :label-width="formLabelWidth"-->
        <el-input v-model="form.address" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>

  </el-card>
</template>

<script>
export default {
  name: "Person",

  data() {
    return{
      form:{},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this. getUser().then(res =>{
      console.log(res)
      this.form = res
    })
  },
  methods:{
   async getUser(){
      return (await this.request.get("/user/username/"+this.user.username)).data
    },

    save(){
      this.request.post("/user/",this.form).then(res=>{
        if(res.code === '200'){
          this.$message.success("添加成功")

          //触发父级更新User的方法
          this.$emit("refreshUser")
          //跟新浏览器保存的用户信息  现在启用了在Manager组件中跟新
          this.getUser().then(res=>{
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res))
          })
        }else{
          this.$message.error("新增失败")
        }
      })
    },

    handleAvatarSuccess(res){
      this.form.avatarUrl = res
    },


  }
}
</script>

<style scoped>
.avatar-uploader{
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}

</style>