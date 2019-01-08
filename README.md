# Disruptor Event Flow Pipeline Prototype

This repository shows an implementation prototype of high performance sequential event processing pipeline using
[LMAX Exchange Disruptor](https://github.com/LMAX-Exchange/disruptor/) based architecture.  


## What is disruptor? 

To make it simple, disruptor is a high performance replacement of queues, suitable for creating any kind of processing 
pipelines with multiple producers and consumers. The key to disruptor architecture is the magical ringbuffer which acts
can be thought of a circular queue where producers and consumers do not have to worry about head and tail. 
The architecture is highly suitable for making responsive systems based on chronology and is event driven. 
The key to high performance of disruptor is the fact that it is totally lock free. LOCKS ARE BAD FOR
PERFORMANCE! Martin Flower, Trisha Gee and Michael Baker have done a commendable job in their blog posts to explain the 
details of disruptor. You can read more about it here:

* [LMAX Exchange's architecture - by Martin Flower](https://martinfowler.com/articles/lmax.html)
* [What's so special about Ringbugfer - by Trisha Gee](http://mechanitis.blogspot.com/2011/06/dissecting-disruptor-whats-so-special.html)
* [Reading from Ringbuffer, the consumer side - by Trisha Gee](http://mechanitis.blogspot.com/2011/06/dissecting-disruptor-how-do-i-read-from.html)
* [Writing to Ringbuffer, the producer side - by Trisha Gee](http://mechanitis.blogspot.com/2011/07/dissecting-disruptor-writing-to-ring.html)
* [Wiring up consumers, creating pipeline - by Trisha Gee](http://mechanitis.blogspot.com/2011/07/dissecting-disruptor-wiring-up.html)
* [Why is disruptor so fast - by Trisha Gee](http://mechanitis.blogspot.com/2011/07/dissecting-disruptor-why-its-so-fast.html)

## Prototype specifications

This prototype aims to show that, an efficient processing pipeline can be created using LMAX Disruptor. 
The prototype, mimics the scenario where multiple producers are responsible to publish events, which gets sequentially 
processed by series of processing stages, eventually batched and flushed to disk. 

The prototype uses [adaptive batching](https://mechanical-sympathy.blogspot.com/2011/10/smart-batching.html) which is an added benefit of using disruptor.

### Todo : Add architecture diagram  

