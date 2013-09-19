#####RSS Reader

This is my way of learning how to do Loaders, Providers, and Services.

The idea is to start a service which polls the RSS feeds for changes, via a 
AsyncTask, and adds the items to a SQLLite database.  This database is accessed 
using a Provider, and the item titles are displayed in a ListFragment, which 
auto updates via the notification sent to it from the provider, when an update 
is made.

This is a fun project, not a serious one, so don't expect to find tests, until 
maybe later, once I've got my head around how the concepts of Loaders, Providers 
and Services.

