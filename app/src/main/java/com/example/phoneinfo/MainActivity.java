package com.example.phoneinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.opengl.GLES20;
import android.opengl.GLES32;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.renderscript.ScriptGroup;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phoneinfo.databinding.ActivityMainBinding;

import org.w3c.dom.Text;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvd;
    private ActivityMainBinding binding;
    DecimalFormat df = new DecimalFormat("#.000000");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        tvd = binding.tv;


        tvd.setText("MANUFACTURER : "+Build.MANUFACTURER + "\n\n");
        tvd.append("\n" +"MODEL : " + Build.MODEL+"\n\n");

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);

        long totalRam = memoryInfo.totalMem;
        long availableRam = memoryInfo.availMem;


        totalRam /= (1024 * 1024);
        availableRam /= (1024 * 1024);
        tvd.append("\n" + "TOTAL RAM : " + totalRam + "MB"+"\n\n");
        tvd.append("\n" + "AVAILABLE RAM : " + availableRam + "MB"+"\n\n");


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
            totalSize = totalSize / (1024 * 1024 * 1024);
            availableSize = availableSize / (1024 * 1024 * 1024);

            // Use the values as needed

            tvd.append("\n" + "TOTAL MEMORY : " + totalSize + "GB"+"\n\n");
            tvd.append("\n" + "AVAILABLE MEMORY : " + availableSize + "GB"+"\n\n");

            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);


            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;


            int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            tvd.append("\n" + "IS CHARGING ? " + isCharging);
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

// Calculate the battery percentage
            float batteryPct = level * 100 / (float) scale;

            tvd.append("\n" + "CHARGING LEVEL : " + batteryPct + "%"+"\n\n");





            String androidVersion = Build.VERSION.RELEASE;

            tvd.append("\n" + "ANDROID VERSION : " + androidVersion+"\n\n");

            CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String[] cameraIds = new String[0];
            try {
                cameraIds = manager.getCameraIdList();
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
            if (cameraIds.length == 0) {
                // No camera available
                return;
            }

            String cameraId = cameraIds[0]; // Use first available camera
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
               requestPermissions(new String[]{android.Manifest.permission.CAMERA},121);

            }
            try {
                manager.openCamera(cameraId, new CameraDevice.StateCallback() {
                    @Override
                    public void onOpened(CameraDevice camera) {
                        try {
                            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                            // Get camera megapixels and aperture info
                            float[] focalLengths = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
                            float aperture = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES)[0];
                            float megapixels = ((characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE).getWidth() *
                                    characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE).getHeight()));

                            tvd.append("\n"+"APERTURE : "+"f"+aperture+"\n\n");
                            tvd.append("\n"+"MEGAPIXELS : "+megapixels+"\n");

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
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }


        }

        // Get CPU information
        String cpu = Build.HARDWARE;


        // Get GPU information
        String gpu = null;
        ActivityManager activityManager2 = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager2.getDeviceConfigurationInfo();
        gpu = configurationInfo.getGlEsVersion();
        tvd.append("\n"+"CPU INFO : "+cpu+"\n\n");


        tvd.append("\n"+"GPU GLE VERSION : "+gpu+"\n\n");

        // get Sensor info
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        Sensor gpsSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        Sensor gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor barometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


// Define a SensorEventListener to listen for sensor changes
        if(gpsSensor!=null) {

            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {

                    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

                        String value = df.format(event.values[0]);
                        binding.gps.setText("GPS : "+value);
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // Not required for this example
                }
            };

// Register the listener for the sensor
            sensorManager.registerListener(sensorEventListener, gpsSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {

            binding.gps.setVisibility(View.GONE);
        }
        if(gyroSensor!=null) {

            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {


                        String value =df.format( event.values[0]);
                        binding.gyro.setText("GYROMETER : "+value);

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // Not required for this example
                }
            };


            sensorManager.registerListener(sensorEventListener, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else binding.gyro.setVisibility(View.GONE);

        if(barometerSensor!=null) {

            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                        String value = df.format(event.values[0]);
                        binding.gps.setText("BAROMETER : "+value);

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // Not required for this example
                }
            };

            sensorManager.registerListener(sensorEventListener, barometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else binding.baro.setVisibility(View.GONE);


        if(accelerometerSensor!=null) {

            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {

                        String value = df.format(event.values[0]);
                        binding.acce.setText("ACCELEROMETER : "+value);

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // Not required for this example
                }
            };

            sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else binding.acce.setVisibility(View.GONE);

        if(rotationSensor!=null) {

            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {

                        String value = df.format(event.values[0]);
                        binding.rot.setText("ROTATION VECTOR : "+value);

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };


            sensorManager.registerListener(sensorEventListener, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else binding.rot.setVisibility(View.GONE);
        if(proximitySensor!=null) {

            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {


                    String value = df.format(event.values[0]);
                    binding.prox.setText("PROXIMITY SENSOR : "+value);

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // Not required for this example
                }
            };

            sensorManager.registerListener(sensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else binding.prox.setVisibility(View.GONE);
        if(lightSensor!=null) {

            SensorEventListener sensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {

                        String value =df.format( event.values[0]);
                        binding.light.setText("LIGHT SENSOR : "+value);

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    // Not required for this example
                }
            };

            sensorManager.registerListener(sensorEventListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else binding.light.setVisibility(View.GONE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==121){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show();
            }

        }
    }
}