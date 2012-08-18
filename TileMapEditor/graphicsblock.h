#ifndef GRAPHICSBLOCK_H
#define GRAPHICSBLOCK_H

#include <QGraphicsItem>
#include <QtCore>
#include <QtGui>
#include "statics.h"
#include "blocktype.h"

class GraphicsBlock : public QGraphicsItem
{
public:
    GraphicsBlock(qreal x, qreal y, qreal width, qreal height, BlockType type);
    QRectF boundingRect() const;
    void paint(QPainter *painter, const QStyleOptionGraphicsItem *option, QWidget *widget);
    BlockType type;
    static bool painting;

private:
    void mousePressEvent(QGraphicsSceneMouseEvent *event);
    void mouseReleaseEvent(QGraphicsSceneMouseEvent *event);
    void hoverMoveEvent(QGraphicsSceneHoverEvent *event);
    void advance(int phase){}
    qreal x;
    qreal y;
    qreal width;
    qreal height;


signals:
    
public slots:
    
};

#endif // GRAPHICSBLOCK_H
