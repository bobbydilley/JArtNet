# JArtNet
A Java ArtNet (DMX Lighting) Library

JArtNet is in very early stages and only supports broadcasting to a specific IP Address. My test setup is using an Arduino with an Ethernet Shield and a DFRobot DMX Shield, coupled with an ArtNet library for the Arduino I found online.

### Supported

- ArtNet BroadCast
- Setting the IP/Port of the Broadcast

### Not Supported

- Multiple Universes
- MultiCast

## How to use

1. Take the folder located at `/src/JArtNet` and move it into your src folder of your Java project
2. At the top of your Java project make sure to add this text:
  ```
  import JArtNet.JArtNet;
  ```
3. You can create a JArtNet object by providing the IP Address of the ArtNet Node, for example:
  ```
  JArtNet artNet = new JArtNet("192.168.0.97");
  ```

4. You can then set DMX values up to 512
  ```
  artNet.set(1, 255); // Setting channel 1 to the value 255
  ```
5. Finally to send the DMX Frame to the device you must call the send method
  ```
  artNet.send();
  ```
