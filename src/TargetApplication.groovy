/**
 * Created by Win10Lxp on 2016/4/24.
 */
class TargetApplication {
    String appName
    String appPath
    String systemDB
    String userDB

    public TargetApplication() {
        appPath = Main.getCurrentRunPath();
        appName = "NoNamed"
        systemDB = "NoNamed"
        userDB = "NoNamed"
    }

    String toString() {
        return "${appName}/(${systemDB},${userDB})@${appPath}"
    }

    /**
     * 修改工程名称
     */
    def updateProjectName() {
        def pName = "${appPath}\\${appName}\\application.properties"
        //def oName = "${appPath}\\${appName}\\application.propertiesA"
        println("关键文件：${pName}")
        def pFile = new File(pName)
        def properties = new Properties()
        try {
            if (pFile.exists()) {
                def fis = new FileInputStream(pFile)
                def isr = new InputStreamReader(fis, "UTF-8")
                properties.load(isr)
                println(properties.getProperty("app.name"))
                //----------------------------------------------------------------------------------------------------------
                properties.setProperty("app.name", this.appName)
                def fos = new FileOutputStream(pFile)
                def osw = new OutputStreamWriter(fos, "UTF-8")
                println(properties.getProperty("app.name"))
                properties.store(osw, "Grails Metadata file")
                println("修改工程名称为：${appName}")
            } else {
                println("找不到关键文件：${pFile.name}")
            }
        } catch (IOException e) {
            println(e.message)
        }
    }

    /**
     * 拷贝文件
     * */
    def copyApplication(Properties properties) {
        def targetApplication = "${appPath}\\${appName}"
        def source = properties.getProperty("主目录")
        def commond = "xcopy ${source} ${targetApplication} /E /I /Y"
        println(commond)
        Runtime runtime = Runtime.getRuntime()
        try {
            runtime.exec(commond)
        } catch (Exception e) {
            println(e)
        }
    }

    /**
     * 处理命令行参数
     */
    def processArgs(String[] args) {
        def argsCount = args.length

        switch (argsCount) {
            case 0:
                appName = "NoNamed"
                systemDB = "NoNamed"
                userDB = "NoNamed"
                break
            case 1:
                //只有一个参数
                appName = args[0]
                systemDB = appName + "_systemDB"
                userDB = appName + "_userDB"
                break
            case 2:
                //有两个参数
                appName = args[0]
                systemDB = args[1]
                userDB = appName + "_userDB"
                break
            case 3:
                //有三个参数
                appName = args[0]
                systemDB = args[1]
                userDB = args[2]
                break
            default:
                appName = args[0]
                systemDB = args[1]
                userDB = args[2]
                break
        }
    }
}
