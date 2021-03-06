from com.android.monkeyrunner import MonkeyRunner, MonkeyDevice
nome = 'User_test'
senha ='a'

#instalando app e preparando dispositivo
device = MonkeyRunner.waitForConnection()
device.installPackage('apkDir')
package = 'br.ufscar.connect'
activity = 'br.ufscar.connect.activities.LoginActivity'
runComponent = package + '/' + activity
device.startActivity(component=runComponent)

#dorme para esperar animacao
MonkeyRunner.sleep(2)
#acende a tela
device.wake()
#seleciona a textbox e digita o username
device.touch(100,200,MonkeyDevice.DOWN_AND_UP)
device.type(nome)
MonkeyRunner.sleep(1)
#avanca para a proxima textbox e digita a senha
device.touch(295,466,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)
device.type(senha)
MonkeyRunner.sleep(1)
#fecha o teclado e clicka em "entrar"
device.press("KEYCODE_BACK",MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(2)
device.touch(171,332,MonkeyDevice.DOWN_AND_UP)




#feed aberto
MonkeyRunner.sleep(15)
device.touch(293,50,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)
device.touch(163,362,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(1)
device.touch(169,158,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.5)
device.touch(175,101,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(0.5)
device.drag((299,382),(299,74),1,10)
MonkeyRunner.sleep(0.5)
device.touch(172,439,MonkeyDevice.DOWN_AND_UP)
MonkeyRunner.sleep(2)

result = device.takeSnapshot()
result.writeToFile('testeAval.png','png')
