from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice

device = MonkeyRunner.waitForConnection()
device.installPackage('apkDir')
package = 'br.ufscar.connect'
activity = 'br.ufscar.connect.activities.LoginActivity'
runComponent = package + '/' + activity

device.startActivity(component=runComponent)
MonkeyRunner.sleep(1)
device.touch(100,450,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)
device.touch(100,310,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)
device.touch(100,123,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)
device.touch(100,349,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(2)
device.type('User_test')
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
device.type('a')
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
device.type('a')
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
device.type('a')
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
device.type('a')
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
device.type('a')
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
device.press("KEYCODE_ENTER", MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(2)

result = device.takeSnapshot()
result.writeToFile('testeSignUP.png','png')