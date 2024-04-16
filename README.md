# Android Barcode Scanner
## This is a final project for ITE5333 Android App Development 1 course at Humber College. 

[x] Authentication with username and password.
We have implemented the Authentication using FireBase Auth.
We have imported the Firebase Auth libraries and provided the Internet Permissions.
The application authenticates and user based on email id and password. 
When you are running the application for the first time, you will need to register a new user.
If user authentication is successful, the customers are allowed to add products by scanning the qr code.

[x] Implementing barcode scanner using Google ML Kit.

We have implemented the barcode scanner using Google ML Kit. For now the app scans for Barcode and gets the data from the Barcode.
We have imported the Google KIT as well as the Camera libraries and provided the necessary permission in Manifest file.
For testing the application you will need to deploy the app in android phone using usb or wifi debugging. As of now the app does not support the Barcode Scan from android simulator. 
We have provided the PreviewView as the input to the InputImage which is passed to Barcode Scanner.

[x] Fetch data for the barcode from firestore.


We have implemented the databse using firestore. We have a products collection and orders collection. Products stores the information of individual products sold in the store and Orders stores order history.

[X] Add recieved product info based on barcode to a list.

We have implemented the barcode scanning which adds the products to the RecyclerView. 
If the duplicate product is scanned multiple times, the application will update the quantity or it will add a new product to the list.


[x] Pass data to a new activity for checkout.

Once the customer has scanned all the products, they can move to checkout to review their products with the associated cost for each product.

[x] Calculate subtotal and total.

The application calculates the amount based on the prices defined in our database for each products.
The calcualtion depends on the quantity of each product added along with the type of the product. 
If the product is set as taxable, an additional taxable amount is added to the total.


[ ] Generate pdf on make payment.


To use the app, install it on a device with camera and scan the below provided barcode. Right now we only have one barcode in the database but this is to test the working of the barcode and the firestore database. Check LogCat for data info such as name, price, isTaxable.

![IMG_1313](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/a3d969ab-067b-47a6-8c61-945b917ebd5d)
![WhatsApp Image 2024-04-15 at 9 41 44 AM](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/67b211b9-1640-4eb6-bd79-e4b7e87b7bd7)
![WhatsApp Image 2024-04-15 at 9 41 44 AM (1)](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/bb66e688-c04f-4109-b0fb-be5f70aefebc)
![WhatsApp Image 2024-04-15 at 9 41 39 AM](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/6c0cb057-34d1-4cc3-84fe-e51eef766578)
![WhatsApp Image 2024-04-15 at 9 41 40 AM](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/89f1e72a-1e07-4bf7-92f5-9db638fded64)
![WhatsApp Image 2024-04-15 at 9 41 40 AM (1)](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/d449bd40-52d0-4ced-a6af-37463f2970f5)
![WhatsApp Image 2024-04-15 at 9 41 41 AM](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/4827e346-7958-4bdc-9977-7acb30b9c350)
![WhatsApp Image 2024-04-15 at 9 41 41 AM (1)](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/5c7b2624-4fcf-4b20-b59c-d5ee73fc1265)
![WhatsApp Image 2024-04-15 at 9 41 42 AM](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/5d57ee2f-cf00-4eb2-b5fb-d03a1540f7a6)
![WhatsApp Image 2024-04-15 at 9 41 42 AM (1)](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/84ad60b3-6654-4be7-a2a4-6401d0d10786)
![WhatsApp Image 2024-04-15 at 9 41 43 AM](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/addc6608-9a32-4c1c-be20-706e000af93b)
![WhatsApp Image 2024-04-15 at 9 41 44 AM](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/f9dbba92-c8bb-43c5-afe2-2a6ac8d8a6f0)
![WhatsApp Image 2024-04-15 at 9 41 44 AM (1)](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/e9306bec-2cdf-41d1-886a-2d1e9ea3f995)
![WhatsApp Image 2024-04-15 at 9 41 43 AM (1)](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/222b09d1-5810-48bf-ad7d-8db8ec1a8594)


![WhatsApp Image 2024-04-15 at 9 50 48 AM](https://github.com/RAKS-Humber/AndroidBarcodeScanner/assets/46134664/a534f8aa-e164-42f8-b38d-2a37a9651322)

