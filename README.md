# aos-caresens-library
---------------
블루투스 기기 통신을 위한 라이브러리(혈당계)
> 샘플 앱 보러 가기 ("https://github.com/huraypositive/aos-caresens-sample")

# Language
* Kotlin

# IDE
* Arctic Fox | 2020.3.1 Patch 3

# Kotlin & SDK Version
* Kotlin Version: 1.5.20
* minSdkVersion: 21
* targetSdkVersion: 31
* compileSdkVersion: 31


# installation
* Step 1. Add the JitPack repository to your build file
```groovy
build.gradle(Project)
allprojects {
  repositories {
    ....
    maven { url 'https://jitpack.io' }
  }
}  
```
* Step 2. Add the dependency
```groovy
 build.gradle(app)
dependencies {
  implementation 'com.github.hurayPositive:aos-caresens-library:$latestVersion'
}
```
* Step 3. Allow Project Repository
```groovy
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven { url "https://jitpack.io" }  // Add this line
    jcenter() // Warning: this repository is going to shut down soon
  }
}
```

# Required Permissions
* AndroidManifest.xml
```xml
  <uses-permission android:name="android.permission.BLUETOOTH" />
  <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
  <uses-permission android:name="android.permission.BLUETOOTH_SCAN"/>
  <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
  <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

# Entity Classes
* ExtendedDevice
  > 디바이스 스캔 결과
  * device: BluetoothDevice
     - device.name : 기기 이름
     - device.address : Mac Address
  * rssi: Int
  * isBonded: Boolean

* GlucoseRecord
  > 혈당 정보
  * sequenceNumber: int
  * time: long
  * glucoseData: double
  * keton: double
  * flag_cs: int
  * flag_hilow: int
  * flag_context: int
  * flag_meal: int
  * flag_fasting: int
  * flag_ketone: int
  * flag_nomark: int 
  * timeoffset: int

* DeviceInfo
  > 기기 정보
  * name: String? 
  * serialNumber: String?
  * version: String?
  * totalcount: Int?
  
# Type Classes
* ScanState
> 블루투스 스캔 연결 상태 유형
* FAIL: 실패
* SCANNING: 스캔중
* STOPPED: 스캔중지
* ConnectState
> 블루투스 기기 연결 상태 유형
* CONNECTING
* CONNECTED
* DISCONNECTED
* ERROR
* UNKNOWN
* DataReadState
> 혈당 데이터 상태 유형
* DEVICE_INFO_READ_COMPLETE
* GlUCOSE_RECORD_READ_COMPLETE
* BLE_DEVICE_NOT_SUPPORTED
* BLE_OPERATE_FAILED
* BLE_OPERATE_NOT_SUPPORTED
* UNKNOWN_ERROR
  
# Interface
* BluetoothInitializeCallbacks
  * fun onSuccess()
  * fun onError(errorMsg: String?)
* BluetoothScanCallbacks
  * fun onScan(state: ScanState, errorMsg: String?, device: ExtendedDevice?)
* BluetoothConnectionCallbacks
  * fun onStateChanged(state: ConnectState, errorMsg: String?, deviceInfo: DeviceInfo?)
* BluetoothDataCallbacks
  * fun onRead(dataReadState: DataReadState, errorMsg: String?, glucoseRecords: SparceArray<GlucoseRecord>?)  

# Functions
* CareSenseBluetoothService
  * startScan():Void 기기의 블루투스 스캔을 시작한다
  * stopScan():Void 기기의 블루투스 스캔을 멈춘다
  * startConnect(address: String?):Void 전달받은 Mac Address에 해당하는 기기에 연결을 요청한다
  * requestAllRecords(): 기기의 모든 혈당 데이터를 수신
  * requestRecordsGreaterOrEqual(sequenceNumber: Int): sequenceNumber보다 큰 혈당 데이터만 수신
  * requestRecentRecord(): 가장 최신 혈당 데이터를 수신

# Usage
* 서비스 등록 (AndroidManifest.xml)
```xml
  <service android:name=".CaresensBluetoothService" android:enabled="true" />
```
  
* 서비스 시작(초기화)
> 하단에 설명되어있는 set Interface 메서드를 활용해서도 상태값 콜백 메서드를 선언 가능합니다
~~~kotlin
  private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val serviceBinder = service as CaresensBluetoothService.ServiceBinder
            caresensBluetoothService = serviceBinder.getService().apply {
                initialize(
                    context: Context,
                    bluetoothInitializeCallbacks: BluetoothInitializeCallbacks,
                    bluetoothScanCallbacks: BluetoothScanCallbacks, 
                    bluetoothConnectionCallbacks: BluetoothConnectionCallbacks,
                    bluetoothDataCallbacks: BluetoothDataCallbacks)
                 
        }

        override fun onServiceDisconnected(name: ComponentName?) {}
  
  private fun serviceBind() {
      val intent = Intent(this, CaresensBluetoothService::class.java)
      bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
  }
~~~

  
* 서비스 종료
~~~kotlin
  caresensBluetoothService?.unbindService(serviceConnection: ServiceConnection)
~~~
  
* 서비스 시작(초기화) 콜백 메서드 설정
~~~kotlin
  caresensBluetoothService?.setBluetoothInitializeCallbacks(bluetoothInitializeCallbacks: BluetoothInitializeCallbacks?)
~~~

* 스캔 시작
~~~kotlin
  caresensBluetoothService?.startScan()
~~~

* 스캔 종료
~~~kotlin
  caresensBluetoothService?.stopScan()
~~~

* 스캔 상태 콜백 메서드 설정
~~~kotlin
  caresensBluetoothService?.setBluetoothScanCallbacks(bluetoothScanCallbacks: BluetoothScanCallbacks?)
~~~
   
* 블루투스 기기와 연결
~~~kotlin
  caresensBluetoothService?.connect(device: BluetoothDevice?)
~~~
  
* 블루투스 기기와 연결 해제
~~~kotlin
  caresensBluetoothService?.disconnect()
~~~
  
* 연결 상태 콜백 메서드 설정
~~~kotlin
  caresensBluetoothService?.setBluetoothConnectionCallbacks(bluetoothConnectionCallbacks: BluetoothConnectionCallbacks?)
~~~

* 전체 혈당 데이터 요청
~~~kotlin
  caresensBluetoothService?.requestAllRecords()
~~~

  
* 일부(시퀀스보다 큰) 혈당 데이터 요청
~~~kotlin
  caresensBluetoothService?.requestRecordsGreaterOrEqual(sequence: Int)
~~~
  
* 데이터 콜백 메서드 설정
~~~kotlin
  caresensBluetoothService?.setBluetoothDataCallbacks(bluetoothDataCallbacks: BluetoothDataCallbacks?)
~~~
