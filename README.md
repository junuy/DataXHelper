#### DataXHelper

DataX的辅助工具

#### 思路

进入数据源数据库，通过`select table_name from information_schema.tables where table_schema = '数据库表名';`获取所有的表名
遍历表名
· 通过`select Column_Name from information_schema.columns where table_schema = '数据库表名' and table_name = '表名';`获取表字段
· 对每一张表，生成一个json文件，文件名称为`数据库表名+表名.json`
· 执行`python datax.py 数据库表名+表名.json`

#### 使用

##### 1、修改application.yml中的配置
包括数据库的地址、账号、密码等，还有dataX的配置，根据注释进行修改

##### 2、自动生成文件
运行DataXHelper.jar，将在dataX.filePath配置的路径下生成各个表的json文件和一个batch.sh脚本

##### 3、执行
将各个表的json文件放置到dataX.pyPath配置的路径下
将batch.sh脚本放置到部署的dataX的bin目录下
赋予batch.sh执行权限后执行`./batch.sh`
即开始数据迁移，等待完成

#### 改进

目前做到了读取数据库，生成所有表的json格式，并且生成批量脚本batch.sh
但是需要将json和batch.sh上传到部署了dataX的服务器下
后续改进思路为将JAR包放到datax服务器上执行，自动完成步骤2、3