# Imports the monkeyrunner modules used by this program
from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice

# Connects to the current device, returning a MonkeyDevice object
device = MonkeyRunner.waitForConnection()

# Installs the Android package. Notice that this method returns a boolean, so you can test
# to see if the installation worked.
device.installPackage('/home/pmario/AndroidStudioProjects/ConnectUFSCar-master/app/build/outputs/apk')

# sets a variable with the package's internal name
package = 'br.ufscar.connect'

# sets a variable with the name of an Activity in the package
activity = 'br.ufscar.connect.activities.LoginActivity'

# sets the name of the component to start
runComponent = package + '/' + activity

# Runs the component
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
# Takes a screenshot
result = device.takeSnapshot()

# Writes the screenshot to a file
result.writeToFile('testeSignUP.png','png')



