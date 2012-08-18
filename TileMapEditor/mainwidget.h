#ifndef MAINWIDGET_H
#define MAINWIDGET_H

#include <QWidget>
#include <QtCore>
#include <QtGui>
#include "statics.h"
#include "graphicsblock.h"

namespace Ui {
class MainWidget;
}

class MainWidget : public QWidget
{
    Q_OBJECT
    
public:
    explicit MainWidget(QWidget *parent = 0);
    ~MainWidget();
    
private slots:
    void on_saveAsButton_clicked();
    void on_loadButton_clicked();
    void on_clearButton_clicked();
    void on_pushButton_clicked();
    void on_listWidget_itemSelectionChanged();

    void on_noClicPainting_stateChanged(int arg1);

private:
    Ui::MainWidget *ui;
    QGraphicsScene *scene;
    void listWidgetUpdate();
    void initGrid();
    void clearBlockList();
    GraphicsBlock *gBlockList[600][800];
    int gridWidth;
    int gridHeight;

};

#endif // MAINWIDGET_H
