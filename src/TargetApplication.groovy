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
        appName ="NoNamed"
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
        def oName = "${appPath}\\${appName}\\application.propertiesA"
        def pFile = new File(pName)
        def properties = new Properties()
        if (pFile.exists()) {
            def fis = new FileInputStream(pFile)
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8")
            properties.load(isr)
            isr.close()
            fis.close()
            println(properties.getProperty("app.name"))
            //----------------------------------------------------------------------------------------------------------
            if (pFile.delete()) {
                properties.setProperty("app.name", this.appName)
                def oFile = new File(pName)
                def fos = new FileOutputStream(oFile)
                try {
                    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8")
                    properties.store(osw, "Grails Metadata file")
                    osw.close()
                    println("修改工程名称为：${appName}")
                }
                catch (IOException e) {
                    println(e.message)
                }
            } else {
                println("无法删除原文件！")
            }
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
     *处理命令行参数
     */
    def processArgs(String[] args) {
        def argsCount = args.length

        switch (argsCount) {
            case 0:
                appName ="NoNamed"
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
