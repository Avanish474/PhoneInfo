# PhoneInfo

**apk download link-->** [apk link](https://drive.google.com/file/d/10exCiXg-nlnlv3wXPdBeU-Ih0IZLKt2F/view?usp=sharing)

This Android app is developed using Java programming language in Android Studio IDE. It retrieves various device information and displays it on the app screen.

## Features
The app retrieves the following device information:

 1. Manufacturer (e.g. Samsung, Oppo)
 2. Model Name and Number
 3. RAM
 4. Storage
 5. Battery current charging level
 6. Android Version
 7. Camera Aperture
 8. Processor (CPU) Information
 9. GPU Information
 10.Live Sensor reading (GPS, Gyroscope, Barometer, Accelerometer, Rotation Vector, Proximity, Ambient light sensor)
 11.IMEI
 
## Requirements
  1. Android Studio IDE
  2. Android device or emulator running Android 4.0 (API level 14) or later

## Installation
  1. Clone or download the project files
  2. Open the project in Android Studio IDE
  3. Connect an Android device or launch an emulator
  4. Build and run the app

## Usage
After launching the app, the device information is automatically retrieved and displayed on the screen in a user-friendly manner.

## Permissions
The app requires the following permissions to function properly:

  1. CAMERA: for accessing camera information
  2. READ_PHONE_STATE: for accessing IMEI number
  3. ACCESS_FINE_LOCATION: for accessing GPS sensor information
  
## Code walthrough

1. ### Getting manufacturer and model name:
  ```
      String manufacturer = Build.MANUFACTURER;
      String model = Build.MODEL;
      String modelname = Build.DEVICE;
  ```   
2. ### RAM information
  To get the RAM information of an Android device in Android Studio using Java, I used the ActivityManager class.
  ```
      ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
      ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
      activityManager.getMemoryInfo(memoryInfo);

      long totalRam = memoryInfo.totalMem;
      long availableRam = memoryInfo.availMem;

      // Convert bytes to megabytes
      totalRam = totalRam / (1024 * 1024);
      availableRam = availableRam / (1024 * 1024);

  ```
  I first get an instance of the ActivityManager class using the getSystemService() method. I then create a new MemoryInfo object and call the getMemoryInfo() method of the ActivityManager class, passing in the MemoryInfo object as a parameter. This fills in the MemoryInfo object with information about the device's memory usage.

  I can then access the total RAM and available RAM information from the MemoryInfo object. Note that these values are returned in bytes, so we convert them to megabytes by dividing by 1024*1024.

  Keep in mind that the amount of available RAM may vary depending on the current usage of the device, so it's important to handle this value with care and not rely on it for critical app functionality.
  
 3. ### Storage Information
  To get the storage information of an Android device in Android Studio using Java, I used the Environment class.
  ```
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
        File externalDir = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(externalDir.getPath());

        long blockSize = statFs.getBlockSizeLong();
        long totalBlocks = statFs.getBlockCountLong();
        long availableBlocks = statFs.getAvailableBlocksLong();

        long totalSize = totalBlocks * blockSize;
        long availableSize = availableBlocks * blockSize;

        // Convert bytes to megabytes
        totalSize = totalSize / (1024 * 1024);
        availableSize = availableSize / (1024 * 1024);

        // Use the values as needed
    }

  ```
  I first checked the state of the external storage using the getExternalStorageState() method of the Environment class. If the state is MEDIA_MOUNTED, then external storage is available for use.

  I then got the path to the external storage directory using the getExternalStorageDirectory() method of the Environment class, and created a new StatFs object using the path.

  I can then get the block size, total blocks, and available blocks from the StatFs object. I used these values to calculate the total size and available size of the external storage in bytes. Finally, I converted these values to megabytes by dividing by 1024*1024.

  Note that this example only gets the information for the external storage. If you want to get the information for the internal storage, you can use the getDataDirectory() method of the Environment class instead of getExternalStorageDirectory().
  
  4. ### Battery Information
   To get the battery current charging status of an Android device in Android Studio using Java, I used the BatteryManager class.
   ```
   
      IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
      Intent batteryStatus = context.registerReceiver(null, ifilter);

      // Are we charging / charged?
      int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
      boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                           status == BatteryManager.BATTERY_STATUS_FULL;

      // How are we charging?
      int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
      boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
      boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
   ```
I first created an IntentFilter to listen for the ACTION_BATTERY_CHANGED intent. I then used the registerReceiver() method of the Context class to register a      receiver for this intent, passing in null as the receiver argument so that the Intent object is returned directly.

I then extracted the current battery status using the getIntExtra() method of the Intent class, passing in the EXTRA_STATUS constant of the BatteryManager class as the key. I checked whether the device is charging or charged by comparing the status to the BATTERY_STATUS_CHARGING or BATTERY_STATUS_FULL constants of the BatteryManager class.

We can also extract information about how the device is charging using the getIntExtra() method, passing in the EXTRA_PLUGGED constant of the BatteryManager class as the key. We check whether the device is charging via USB or AC power by comparing the value to the BATTERY_PLUGGED_USB or BATTERY_PLUGGED_AC constants of the BatteryManager class.

Note that to use the BatteryManager class, you need to have the android.permission.BATTERY_STATS permission in your app's manifest.

To get the battery current charging level of an Android device :
```
    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    Intent batteryStatus = context.registerReceiver(null, ifilter);

    // Get the current battery level and scale
    int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

    // Calculate the battery percentage
    float batteryPct = level * 100 / (float)scale;

```
I first created an IntentFilter to listen for the ACTION_BATTERY_CHANGED intent. I then used the registerReceiver() method of the Context class to register a receiver for this intent, passing in null as the receiver argument so that the Intent object is returned directly.

I then extracted the current battery level and scale using the getIntExtra() method of the Intent class, passing in the EXTRA_LEVEL and EXTRA_SCALE constants of the BatteryManager class as the keys.

We can calculate the battery percentage by dividing the level by the scale and multiplying by 100.

Note that to use the BatteryManager class, you need to have the android.permission.BATTERY_STATS permission in your app's manifest.

  5.### Android Version
   ```
   String androidVersion = Build.VERSION.RELEASE;

   ```
  
  6.### Camera Information
  To get camera information of an Android device in Android Studio using Java, you can use the Camera or Camera2 API. I used Camera2 API.
  For using the camera2 api you need to set following permissions in Manifest file.
  ```
     <uses-permission android:name="android.permission.CAMERA" />
     <uses-feature android:name="android.hardware.camera2.full" />

  ```
  
  ```
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String[] cameraIds = manager.getCameraIdList();
        if (cameraIds.length == 0) {
            // No camera available
            return;
        }
        String cameraId = cameraIds[0]; // Use first available camera
        manager.openCamera(cameraId, new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice camera) {
                try {
                    CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                    // Get camera megapixels and aperture info
                    float[] focalLengths = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
                    float aperture = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES)[0];
                    float megapixels = ((characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE).getWidth() *
                            characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE).getHeight()) /
                            1000000.0f);
                    Log.d(TAG, "Focal length: " + Arrays.toString(focalLengths));
                    Log.d(TAG, "Aperture: " + aperture);
                    Log.d(TAG, "Megapixels: " + megapixels);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDisconnected(CameraDevice camera) {
                // Camera device disconnected
            }

            @Override
            public void onError(CameraDevice camera, int error) {
                // Error opening camera device
            }
        }, null);

  ```
This code snippet is written in Java and is used to retrieve camera information from an Android device using the Camera2 API. The code starts by getting an instance of the CameraManager class using the getSystemService() method. The CAMERA_SERVICE argument is passed to getSystemService() to retrieve a CameraManager object.

Next, the available camera IDs are retrieved using the getCameraIdList() method of the CameraManager object. If there are no cameras available, the method returns and the code stops. Otherwise, the first available camera is selected using cameraIds[0].

The openCamera() method of the CameraManager object is called to open the camera device. The StateCallback() class is used to monitor the state of the camera device. When the camera device is opened, the onOpened() method is called, which retrieves the camera characteristics using the getCameraCharacteristics() method of the CameraManager object. The camera megapixels and aperture info are retrieved using the get() method of the CameraCharacteristics object.

Finally, the retrieved camera information is logged to the console using the Log.d() method. If an error occurs while opening the camera device, the onError() method is called. If the camera device is disconnected, the onDisconnected() method is called.

  7.### CPU and GPU Information 
   To get CPU and GPU information in an Android app using Java, you can use the Build class provided by the Android SDK. Here's an example code that retrieves CPU and    GPU information.
   ```
       // Get CPU information
       String cpu = Build.HARDWARE;

       // Get GPU information
       String gpu = null;
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
           ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
           gpu = configurationInfo.getGlEsVersion();
       }

   ```
In the above code, Build.HARDWARE is used to get the CPU information. This returns a string representing the hardware name of the device.

To get the GPU information, I first checked if the device is running on Android 5.0 (API level 21) or later, as the getDeviceConfigurationInfo method was introduced in that version. We then get the ConfigurationInfo object using the ActivityManager, and call the getGlEsVersion method to get the OpenGL ES version, which can be used to determine the GPU model.

Note that the getGlEsVersion method may return null on some devices, in which case you may need to use other methods to get the GPU information.

  8.### Sensor Information
  To get live sensor data in an Android app using Java, you can use the SensorManager class provided by the Android SDK. Here's an example code that retrieves live sensor data for GPS, gyroscope, barometer, accelerometer, rotation vector, proximity, and ambient light sensor:
  ```
          // Initialize sensor manager
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Get GPS sensor data
        Sensor gpsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LOCATION);
        sensorManager.registerListener(this, gpsSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Get gyroscope sensor data
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Get barometer sensor data
        Sensor barometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorManager.registerListener(this, barometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Get accelerometer sensor data
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Get rotation vector sensor data
        Sensor rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Get proximity sensor data
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Get ambient light sensor data
        Sensor ambientLightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, ambientLightSensor, SensorManager.SENSOR_DELAY_NORMAL);

  ```
  In the above code, SensorManager is used to initialize the sensor manager, and getDefaultSensor is used to get the default sensor for each sensor type. The registerListener method is then used to register a listener that will receive sensor data.

Note that the above code assumes that the calling class implements the SensorEventListener interface, and has implemented the onSensorChanged and onAccuracyChanged methods, which will receive the sensor data. You will need to process the sensor data appropriately based on your use case.
