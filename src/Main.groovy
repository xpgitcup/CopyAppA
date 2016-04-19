/**
 * Created by Win10Lxp on 2016/4/16.
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
        def seedApplicationPath
        def seedProperties = new java.util.Properties()

        args.each {e->
            println(e)
        }
        //读取配置文件，获取种子程序的位置
        def iniFile = new File("SeedApplication.ini")
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
        xcopyApplication(args, seedProperties)
        //调用系统命令，拷贝文件
        //修改文件
    }

    /**
     * 获取当前目录
     * */
    def static currentPath() {
        def f = new File(".")
        return f.canonicalPath
    }

    /**
     * 拷贝种子程序到目标程序目录
     * */
    def static xcopyApplication(String[] args, Properties properties) {
        println("${args} ---- ${properties}")
        def targetApplication
        def source = properties.getProperty("主目录")
        def targetPath
        if (args.length>0) {
            targetApplication = args[0]
            if (!targetApplication.contains("\\")) {
                targetApplication = currentPath() + "\\" + targetApplication
            }
            println("目标：" + targetApplication)
        }
        def commond = "xcopy ${source} ${targetApplication} /E /I /Y"
        println(commond)
        Runtime runtime=Runtime.getRuntime()
        try {
            runtime.exec(commond)
        } catch (Exception e) {
            println(e)
        }
    }
}
