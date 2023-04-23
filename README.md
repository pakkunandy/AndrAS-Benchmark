# AndrAS-Bench
This is an open test suite for evaluating the effectiveness of threat modeling for Android apps.

This version comprises the following test cases:

## Data Storage
* RealmDB-SQLInjection
* SharePreferences-InformationLeak
* KeyStore-InformationLeak
* SQLiteDB-ConfusedDeputy

## HTTP Connection
* Volley-MITM*
* HttpsUrlConnection-MITM*
* DefaultHttpClient-*
* AndroidHttpClient-*
* OkHttpClient-*
* Retrofit-*

## Exported Components
* Insecure-ExportedService
* Insecure-ExportedContentProvider

## SensitiveConnection
* Bluetooth-InformationLeak*
* Bluetooth-MITM*
* NFC-InformationLeak*
* SMS-PrivacyLeak*
* SMS-Spoofing*
* Calendar-PrivacyLeak*
* Contacts-PrivacyLeak*
* Sensors-PrivacyLeak*
* Location-PrivacyLeak*
