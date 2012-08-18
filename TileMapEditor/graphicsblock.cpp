#include "graphicsblock.h"

bool GraphicsBlock::painting;

GraphicsBlock::GraphicsBlock(qreal x, qreal y, qreal width, qreal height, BlockType type)
{
    this->x = x;
    this->y = y;
    this->width = width;
    this->height = height;
    this->type = type;
    this->setAcceptsHoverEvents(true);
}

QRectF GraphicsBlock::boundingRect() const
{
    return QRectF(x, y, height, width);
}

void GraphicsBlock::paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget)
{
    QRectF rec = boundingRect();

    QBrush brush = QBrush(type.color);
    painter->setBrush(brush);
    painter->drawRect(rec);
}

void GraphicsBlock::mousePressEvent(QGraphicsSceneMouseEvent *event)
{
    this->type = Statics::currentBlockType;
    this->update();
}

void GraphicsBlock::mouseReleaseEvent(QGraphicsSceneMouseEvent *event)
{
    this->update();
}

void GraphicsBlock::hoverMoveEvent(QGraphicsSceneHoverEvent *event)
{
    if(painting)
    {
        this->type = Statics::currentBlockType;
        this->update();
    }
}


