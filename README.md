# aos-caresens-library
---------------
블루투스 기기 통신을 위한 라이브러리(혈당계)
> 샘플 앱 보러 가기 ("https://github.com/huraypositive/aos-caresens-sample")

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
  * sequenceNumber:
  * time:
  * glucoseData:
  * flag_cs
  * flag_hilow
  * flag_context
  * flag_meal
  * flag_fasting
  * flag_ketone
  * flag_nomark
  * timeoffset

#Interface
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
