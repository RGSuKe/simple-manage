<template>
  <div>


    <div style="margin: 10px 0">
      <el-input style="width: 200px" suffix-icon="el-icon-search" v-model="username" placeholder="请输入名称"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-message" v-model="nickname" placeholder="请输入名字" class="ml-5"></el-input>
      <el-input style="width: 200px" suffix-icon="el-icon-s-home" v-model="address" placeholder="请输入地址" class="ml-5"></el-input>

      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="primary" @click="reset">重置</el-button>
    </div>

      <div style="margin: 10px 0">
        <el-button type="primary" @click="handleAdd">新增<i class="el-icon-plus"></i></el-button>
        <el-popconfirm
            class="ml-5"
            confirm-button-text='确定'
            cancel-button-text='再想想'
            icon="el-icon-info"
            icon-color="red"
            title="确定删除吗？"
            @confirm="delBatch"
        >
          <el-button type="danger" slot="reference">批量删除<i class="el-icon-minus"></i></el-button>
        </el-popconfirm>
        <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelSunccess" style="display: inline-block">
          <el-button type="primary" class="ml-5" >导入<i class="el-icon-upload2"></i></el-button>
        </el-upload>
        <el-button type="primary" class="ml-5" @click="exp">导出<i class="el-icon-download"></i></el-button>
      </div>

    <el-table :data="tableData" border stripe header-cell-class-name="headerBg"  @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="140">
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="140">
      </el-table-column>
      <el-table-column prop="nickname" label="姓名" width="120">
      </el-table-column>
      <el-table-column prop="email" label="邮箱">
      </el-table-column>
      <el-table-column prop="address" label="地址">
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)" >编辑<i class="el-icon-edit"></i></el-button>

          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除吗？"
              @confirm="handledelete(scope.row.id)"
          >
            <el-button type="danger" slot="reference">删除<i class="el-icon-close"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="40%" >
      <el-form label-width="120px" size="small">
        <el-form-item label="用户名"  ><!--  :label-width="formLabelWidth"-->
          <el-input v-model="form.username" auto-complete="off"></el-input>
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      form: {},
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:10,
      username:'',
      nickname:'',
      address:'',
      dialogFormVisible:false,
      multipleSelection:[],
      headerBg:"headerBg"
    }
  },
  created() {
    this.load()
  },
  methods:{
    reset(){
      this.username=""
      this.nickname=""
      this.address=""
      this.load()
    },
    load(){
      this.request.get("http://localhost:9090/user/page/",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          nickname: this.nickname,
          address: this.address
        }
      }).then(res=>{
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
      //请求分页查询是
      // fetch("http://localhost:9090/user/page/?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username+"&email="+this.email+"&address="+this.address).then(res=>res.json()).then(res=>{
      //   console.log(res)
      //   this.tableData = res.data;
      //   this.total = res.total;
      // })
    },

    handleAdd(){
      this.dialogFormVisible=true
      this.form={}
    },

    save(){
      this.request.post("/user/",this.form).then(res=>{
        if(res.code === "200"){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },

    handledelete(id){
      this.request.delete("/user/"+ id).then(res=>{
        if(res.code === "200"){
          this.$message.success("删除成功")

          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },

    handleEdit(row){
      this.form=JSON.parse(JSON.stringify(row))
      this.dialogFormVisible=true

    },

    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/user/del/betch", ids).then(res=>{
        if(res.code === "200"){
          this.$message.success("批量删除成功")

          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    handleSizeChange(pageSize){
      this.pageSize=pageSize;
      this.load()
    },

    handleCurrentChange(pageNum){
      this.pageNum=pageNum;
      this.load()
    },

    exp(){
      window.open("http://localhost:9090/user/export")
    },

    handleExcelSunccess(){
      this.$message.success("导入成功")
      this.load()
    }


  }
}
</script>

<style scoped>
  .headerBg{
    background-color: #cccccc!important;
  }
</style>