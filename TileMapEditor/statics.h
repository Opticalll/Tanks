#ifndef STATICS_H
#define STATICS_H

#include <QList>
#include "blocktype.h"

class Statics
{
public:
    Statics(){}
    static bool draw;
    static QList<BlockType> blockTypeList;
    static BlockType currentBlockType;

};

#endif // STATICS_H
