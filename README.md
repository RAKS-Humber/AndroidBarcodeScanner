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
