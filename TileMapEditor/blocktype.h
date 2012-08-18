#ifndef BLOCKTYPE_H
#define BLOCKTYPE_H

#include <QColor>

class BlockType
{
public:
    BlockType(){}
    BlockType(int typeID, QColor color, QString Name){this->typeID = typeID; this->color = color; this->Name = Name;}
    QColor color;
    QString Name;
    int typeID;
};

#endif // BLOCKTYPE_H
