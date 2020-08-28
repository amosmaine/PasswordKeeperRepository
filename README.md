# PasswordKeeperRepository


This was a just for fun project done because i needed an app to put all my passwords in.
I know that at first sight it seems that it's something unsecure, but it helps since it's more and more secure than
a post-it left in the kitchen along with all passwords on it. 
The idea is very easy: at the start of the app, a fingerprinted authentication is asked, to guarantee (at least at the 99 percent of cases)
that it's the phone owner who is logging in. Then all passwords are displayed and, on another page, new codes/passwords can be added or removed.



Why it secure? 1. w/o the fingerprint, no password is displayed.

2. all codes/passwords are encrypted in the database which the app uses to store things in.





HOW THE ENCRYPTION WORKS

The first time the app is started, a symmetric cryptographic key is generated: it is immediately stored in an EncryptedSharedPreference.
The EncryptedSharedPreference can be seen as a file, encrypted with a key, stored securely in the android keyStore. To encrypt and decrypt
things in the database, the first generated key is used. Whenever that key is needed, it is given in a secure way, since it is unlocked
using Google's android API. 



I hope you can try it, and look at the code if something can be done better. ENJOY!
