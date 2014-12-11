PeopleFinder
============

Using the Amazon SimpleDb

Requests to SimpleDb are made through a client
Contact details are stored as items in a SimpleDb domain
An item is a collection of attribute/value pair
Select request uses a simple query language to determine which items to match and what data to return or how

Making a call

The INTENT, ACTION_CALL  is used
The phone number to be dialed is set as the data
The activity is then started
This feature can be used by swiping the list item from left to right

Adding to Contacts

ContactsContract defines an extensible database of contact-related information
Batch method is used to insert the raw contact and its constituent data rows in a single database transaction and causes at most one aggregation pass
This feature can be used by swiping the list item from right to left
After the contact has been successfully added, a toast notifies the same

========================================================================

Please download the PeopleFinder.apk file located here- https://github.com/sagar-sinha/PeopleFinder/blob/master/bin/PeopleFinder.apk


