# PhoneInfo

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
  
  
   
