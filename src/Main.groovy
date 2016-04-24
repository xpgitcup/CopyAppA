/**
 * Created by Win10Lxp on 2016/4/16.
 * CopyAppA  程序名称 系统数据库名称 用户数据库名称
 * 当前目录就是目标程序所在目录
 * 系统数据库：程序名称_systemdb
 * 用户数据库：程序名称_userdb
 */
class Main {
    /**
     * 根据种子程序，拷贝生成目标程序
     * 种子程序的原位置依据ini文件配置
     * 参数：
     * 第一：目标程序的名字
     * 第二：目标数据库的名称，考虑有两个以上数据库的情况
     * 拷贝文件
     * 修改程序名
     * 修改数据库名称
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //变量定义
        def seedApplicationPath
        def seedProperties = new java.util.Properties()

        //处理命令行
        if (args.length < 1) {
            println("程序使用方法：")
            println("CopyAppA  程序名称 系统数据库名称 用户数据库名称")
            println("当前目录就是目标程序所在目录")
            println("系统数据库：程序名称_systemdb")
            println("用户数据库：程序名称_userdb")
        } else {
            println("当前命令行：")
            args.each { e ->
                println(e)
            }
        }

        Main main = new Main()
        def currentPath = main.getCurrentPath()
        //读取配置文件，获取种子程序的位置
        def iniFile = new File("${currentPath}/SeedApplication.ini")
        if (iniFile.exists()) {
            def fis = new FileInputStream(iniFile)
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8")
            seedProperties.load(isr)
            seedProperties.eachWithIndex() { Map.Entry<Object, Object> entry, int i ->
                println("${i}: ${entry.key}->${entry.value}")
            }
        } else {
            println("当前目录：${iniFile.absolutePath}")
            println("SeedApplication.ini 文件不存在！")
        }
        //分析参数，生成目标程序的路径
        //调用系统命令，拷贝文件
        xcopyApplication(args, seedProperties)
        //修改文件
        //修改程序名----

        //创建类对象
        def targetApplication = new TargetApplication()
        targetApplication.processArgs(args)
        println(targetApplication)
        //println(this.getClass().getResource("/").getPath())

        //System.out.println(System.getProperty("java.class.path"));    //成功，但是不是想要的结果

    }

    /**
     * 获取当前目录
     * */
    def static getCurrentRunPath() {
        def f = new File(".")
        return f.canonicalPath
    }

    /*
    *获取当前目录
    * */
    def getCurrentPath() {
        println("当前类加载目录是：")
        def loader = this.getClass().getClassLoader()
        //def res = loader.getResources("")
        //res.each {it->
        //    println("hi ${it}")
        //}
        //println("-------")
        //println(res)
        //println(loader.getResource("Main.class").getPath())
        //println(loader.getResource("Main.class").getFile())
        def temp = loader.getResource("Main.class").getFile()
        def k = temp.indexOf("Main.class")
        def tempa = temp.substring(0, k-1)
        //println("-------${tempa}")
        if (tempa.indexOf(".jar!")) {
            k = tempa.lastIndexOf("/")
            tempa = tempa.substring(0, k)
        }
        println("-------${tempa}")
        if (tempa.indexOf("file:/")>-1) {
            tempa = tempa.substring(5)
        }
        println("-------${tempa}")
        return tempa
    }

    /**
     * 拷贝种子程序到目标程序目录
     * */
    def static xcopyApplication(String[] args, Properties properties) {
        println("${args} ---- ${properties}")
        def targetApplication
        def source = properties.getProperty("主目录")
        def targetPath
        if (args.length > 0) {
            targetApplication = args[0]
            if (!targetApplication.contains("\\")) {
                targetApplication = getCurrentRunPath() + "\\" + targetApplication
            }
            println("目标：" + targetApplication)
        }
        def commond = "xcopy ${source} ${targetApplication} /E /I /Y"
        println(commond)
        Runtime runtime = Runtime.getRuntime()
        try {
            runtime.exec(commond)
        } catch (Exception e) {
            println(e)
        }
    }
}
