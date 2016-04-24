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
