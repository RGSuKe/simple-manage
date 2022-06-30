<template>
  <div>

    <div style="margin: 10px 0">
      <el-input style="width: 200px" suffix-icon="el-icon-search" v-model="name" placeholder="请输入名称"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="primary" @click="reset">重置</el-button>
    </div>
      <div style="margin: 10px 0">
      <el-upload action="http://localhost:9090/file/upload" :show-file-list="false"  :on-success="handleFileUploadSunccess" style="display: inline-block">
        <el-button type="primary" class="ml-5" >上传<i class="el-icon-upload2"></i></el-button>
      </el-upload>
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

    </div>
      <div>
        <el-table :data="tableData" border stripe header-cell-class-name="headerBg"  @selection-change="handleSelectionChange">
          <el-table-column
              type="selection"
              width="55">
          </el-table-column>
          <el-table-column prop="id" label="ID" width="140"></el-table-column>
          <el-table-column prop="name" label="文件名称" width="140"></el-table-column>
          <el-table-column prop="type" label="文件类型" width="120"></el-table-column>
          <el-table-column prop="size" label="文件大小（kb）"></el-table-column>
          <el-table-column label="下载">
            <template slot-scope="scope">
              <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
            </template>
          </el-table-column>
          <el-table-column label="启用">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
            </template>
          </el-table-column>


          <el-table-column label="操作">
            <template slot-scope="scope">
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
      </div>

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
  </div>


</template>

<script>
export default {
  name: "File",
  data(){
    return{
      tableData: [],
      name: [],
      multipleSelection:[],
      total:0,
      pageNum:1,
      pageSize:10,
    }
  },
  created() {
    this.load()
  },
  methods:{
    reset(){
      this.name=""
      this.load()
    },
    load(){
      this.request.get("/file/page/",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
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


    handledelete(id){
      this.request.delete("/file/"+ id).then(res=>{
        if(res.code === '200'){
          this.$message.success("删除成功")

          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },


    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },


    delBatch(){
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/file/del/betch", ids).then(res=>{
        if(res.code === '200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
      this.load()
    },
    handleSizeChange(pageSize){
      this.pageSize=pageSize;
      this.load()
    },

    handleCurrentChange(pageNum){
      this.pageNum=pageNum;
      this.load()
    },

    handleFileUploadSunccess(res){
      console.log(res)
    },

    download(url){
      window.open(url)
    },

    changeEnable(row){
      this.request.post("/file/update", row).then(res=>{
        if (res.code === '200'){
          this.$message.success("操作成功")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>