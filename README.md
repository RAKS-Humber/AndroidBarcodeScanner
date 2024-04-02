# Android Barcode Scanner
## This is a final project for ITE5333 Android App Development 1 course at Humber College. 

[ ] Authentication with username and password.

[x] Implementing barcode scanner using Google ML Kit.

We have implemented the barcode scanner using Google ML Kit. For now the app scans for Barcode and gets the data from the Barcode.
We have imported the Google KIT as well as the Camera libraries and provided the necessary permission in Manifest file.
For testing the application you will need to deploy the app in android phone using usb or wifi debugging. As of now the app does not support the Barcode Scan from android simulator. 
We have provided the PreviewView as the input to the InputImage which is passed to Barcode Scanner.

[x] Fetch data for the barcode from firestore.

We have implemented the databse using firestore. We have a products collection and orders collection. Products stores the information of individual products sold in the store and Orders stores order history.

[ ] Add recieved product info based on barcode to a list.

[ ] Pass data to a new activity for checkout.

[ ] Calculate subtotal and total.

[ ] Generate pdf on make payment.


To use the app, install it on a device with camera and scan the below provided barcode. Right now we only have one barcode in the database but this is to test the working of the barcode and the firestore database. Check LogCat for data info such as name, price, isTaxable.

![IMG_1313](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/a3d969ab-067b-47a6-8c61-945b917ebd5d)
