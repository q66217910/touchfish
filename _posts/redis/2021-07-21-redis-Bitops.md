---
layout: post
title: REDIS 数据类型之 Bitops
category: redis
tags: [redis]
no-post-nav: true
---

REDIS 数据类型之 Bitops
===



位图的存储是基于sds字符串，最大512M。



## 1.Bitops于redis作用(位图操作)

- String类型的**SETBIT**操作: 
- String类型的**GETBIT**操作





## 2.Bitops的GETBIT操作

**GETBIT key offset** 



**getBitOffsetFromArgument:**获取请求的参数

**sdsEncodedObject:**判断数据类型**OBJ_ENCODING_RAW(SDS)**/**OBJ_ENCODING_EMBSTR(ZIPLIST)**S

```C
void getbitCommand(client *c) {
    robj *o;
    char llbuf[32];
    size_t bitoffset;
    size_t byte, bit;
    size_t bitval = 0;
	//从请求参数中获取要判断的bit位
    if (getBitOffsetFromArgument(c,c->argv[2],&bitoffset,0,0) != C_OK)
        return;

    if ((o = lookupKeyReadOrReply(c,c->argv[1],shared.czero)) == NULL ||
        checkType(c,o,OBJ_STRING)) return;

    byte = bitoffset >> 3;
    bit = 7 - (bitoffset & 0x7);
    if (sdsEncodedObject(o)) {
        //存储数据类型为SDS
        if (byte < sdslen(o->ptr))
            //根据SDS的值bits的偏移量做为位运算
            bitval = ((uint8_t*)o->ptr)[byte] & (1 << bit);
    } else {
        if (byte < (size_t)ll2string(llbuf,sizeof(llbuf),(long)o->ptr))
            bitval = llbuf[byte] & (1 << bit);
    }

    addReply(c, bitval ? shared.cone : shared.czero);
}
```



## 3.Bitops的SETBIT操作

 **SETBIT key offset value** 



```c
void setbitCommand(client *c) {
    robj *o;
    char *err = "bit is not an integer or out of range";
    size_t bitoffset;
    ssize_t byte, bit;
    int byteval, bitval;
    long on;

    if (getBitOffsetFromArgument(c,c->argv[2],&bitoffset,0,0) != C_OK)
        return;

    if (getLongFromObjectOrReply(c,c->argv[3],&on,err) != C_OK)
        return;

    //要设置的值得是0和1
    if (on & ~1) {
        addReplyError(c,err);
        return;
    }

    if ((o = lookupStringForBitCommand(c,bitoffset)) == NULL) return;

    //获取当前的bit值
    byte = bitoffset >> 3;
    byteval = ((uint8_t*)o->ptr)[byte];
    bit = 7 - (bitoffset & 0x7);
    bitval = byteval & (1 << bit);

    //更新新的bit值
    byteval &= ~(1 << bit);
    byteval |= ((on & 0x1) << bit);
    ((uint8_t*)o->ptr)[byte] = byteval;
    signalModifiedKey(c,c->db,c->argv[1]);
    notifyKeyspaceEvent(NOTIFY_STRING,"setbit",c->argv[1],c->db->id);
    server.dirty++;
    addReply(c, bitval ? shared.cone : shared.czero);
}

//没有则创建一个sds,有则讲sds转换为ZIPLIST
robj *lookupStringForBitCommand(client *c, size_t maxbit) {
    size_t byte = maxbit >> 3;
    robj *o = lookupKeyWrite(c->db,c->argv[1]);
    if (checkType(c,o,OBJ_STRING)) return NULL;

    if (o == NULL) {
        o = createObject(OBJ_STRING,sdsnewlen(NULL, byte+1));
        dbAdd(c->db,c->argv[1],o);
    } else {
        o = dbUnshareStringValue(c->db,c->argv[1],o);
        o->ptr = sdsgrowzero(o->ptr,byte+1);
    }
    return o;
}
```

