<template>
  <div>
    <el-row :span="10" style="margin-bottom: 30px">
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div ><i class="el-icon-user"></i>用户总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-user"></i>用户总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #E6A23C ">
          <div ><i class="el-icon-user"></i>用户总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card  style="color: #F56C6C">
          <div><i class="el-icon-user"></i>用户总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
    </el-row>


    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 400px;"></div>
      </el-col>

      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px;"></div>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "Home",
  data(){
    return{

    }
  },
  mounted(){
    //折线图

    var option = {
      title:{
        text: '各季度人数',
        subtext: '趋势图',
        left: 'center',
      },
      xAxis: {
        type: 'category',
        data: ["第1季度","第2季度","第3季度","第4季度",]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        {
          data: [],
          type: 'bar'
        }
      ]
    };
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);



    // 饼图
    var pieoption={
      title:{
        text: '各季度人数',
        subtext: '比例图',
        left: 'center',
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '70%',
          label:{//在饼图里面显示数字
            normal:{
              show:true,
              position: 'inner',
              textStyle:{
                fontWeight: 200,
                fontSize: 13,
                color: '#fff',
              },
              formatter:'{d}%'
            }
          },
          data: [],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };

      var pieDom = document.getElementById('pie');
      var pieChart = echarts.init(pieDom);


    this.request.get("/echarts/members").then(res=>{
      // option.series[0].data = res.data.x
      // option.series[1].data = res.data.y
      option.series[0].data = res.data
      option.series[1].data = res.data
      myChart.setOption(option);

      pieoption.series[0].data = [
        {name:"第1季度",value: res.data[0]},
        {name:"第2季度",value: res.data[1]},
        {name:"第3季度",value: res.data[2]},
        {name:"第4季度",value: res.data[3]},
      ]
      pieChart.setOption(pieoption);
    })


  }
}
</script>

<style scoped>

</style>