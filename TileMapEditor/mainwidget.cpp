#include "mainwidget.h"
#include "ui_mainwidget.h"

bool Statics::draw;
QList<BlockType> Statics::blockTypeList;
BlockType Statics::currentBlockType;

MainWidget::MainWidget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::MainWidget)
{
    ui->setupUi(this);
    ui->saveAsButton->setIcon(QIcon(QPixmap(":/Icons/Save")));
    ui->loadButton->setIcon(QIcon(QPixmap(":/Icons/Load")));
    ui->clearButton->setIcon(QIcon(QPixmap(":/Icons/Clear")));
    ui->listWidget->setSelectionBehavior(QAbstractItemView::SelectRows);
    scene = new QGraphicsScene(this);
    ui->graphicsView->setScene(scene);
    gridWidth = 40;
    gridHeight = 30;
    initGrid();
    Statics::blockTypeList.push_back(BlockType(0, QColor(0, 0, 0), "Void"));
    Statics::currentBlockType = Statics::blockTypeList[0];
    GraphicsBlock::painting = false;

}

MainWidget::~MainWidget()
{
    delete ui;
}

void MainWidget::on_saveAsButton_clicked()
{
    QString filePath = QFileDialog::getSaveFileName(this, "Choose Block File", "Choose file");
    QFile file(filePath);

    if(file.open(QIODevice::WriteOnly | QIODevice::Text))
    {
        QString outputString;
        for(int i = 0; i < gridHeight; i++)
        {
            for(int z = 0; z < gridWidth; z++)
            {
                outputString = outputString + QString::number(gBlockList[i][z]->type.typeID) + " ";
            }
            outputString = outputString + "99\n";
        }
        QTextStream out(&file);
        out << outputString;
    }
    else
        return;
}

void MainWidget::on_loadButton_clicked()
{
    QString filePath = QFileDialog::getOpenFileName(this, "Choose Block File", "Choose file");
    QFile file(filePath);

    if(file.open(QIODevice::ReadOnly | QIODevice::Text))
    {
        QString text = file.readAll();
        QStringList lines = text.split("\n");
        for(int i = 0; i < gridHeight; i++)
        {
            QStringList ids = lines[i].split(" ");
            for(int z = 0; z < gridWidth; z++)
            {

                bool ok = true;
                int id = ids[z].toInt(&ok, 10);
                if(!ok)
                {
                    QMessageBox::information(this, "Warning", "File is corrupted.", "Ok");
                    return;
                }
                if(id > Statics::blockTypeList.size() + 1)
                {
                    QMessageBox::information(this, "Warning", "You must load right Block type list.", "Ok");
                    return;
                }
                gBlockList[i][z]->type = Statics::blockTypeList[id];
            }
        }
    }
    else
        return;
    scene->update();
}

void MainWidget::on_clearButton_clicked()
{
    for(int i = 0; i < gridHeight; i++)
    {
        for(int z = 0; z < gridWidth; z++)
        {
            BlockType basic = BlockType(0, QColor(255, 255, 255), "Void");
            gBlockList[i][z]->type = basic;
        }
    }
    scene->update();
}

void MainWidget::on_pushButton_clicked()
{
    //load button types;
    QString filePath = QFileDialog::getOpenFileName(this, "Choose Block File", "Choose file");
    QFile file(filePath);

    if(file.open(QIODevice::ReadOnly | QIODevice::Text))
    {
        QString text = file.readAll();
        QStringList lines = text.split("\n");
        QList<BlockType> blockList;
        for(int i = 0; i < lines.size(); i++)
        {
            QStringList blocktype = lines[i].split("|");
            bool ok = true;
            int id = blocktype[0].toInt(&ok, 10);
            if(!ok)
                return;
            QStringList rgb = blocktype[1].split(".");
            int r = rgb[0].toInt(&ok, 10);
            if(!ok)
                return;
            int g = rgb[1].toInt(&ok, 10);
            if(!ok)
                return;
            int b = rgb[2].toInt(&ok, 10);
            if(!ok)
                return;
            QString name = blocktype[2];
            blockList.push_back(BlockType(id, QColor(r, g, b), name));
        }
        Statics::blockTypeList = blockList;
        listWidgetUpdate();
        file.close();
    }
    else
        return;
}

void MainWidget::listWidgetUpdate()
{
    ui->listWidget->clear();
    QList<BlockType> blockList = Statics::blockTypeList;
    for(int i = 0; i < blockList.size(); i++)
    {
        QListWidgetItem *item = new QListWidgetItem(ui->listWidget, i);
        item->setText(QString(QString::number(blockList[i].typeID) + " - " + blockList[i].Name));
        QPixmap icon = QPixmap(15, 15);
        icon.fill(blockList[i].color);
        icon.save("output.jpg");
        item->setIcon(QIcon(icon));
        ui->listWidget->addItem(item);
    }
}

void MainWidget::initGrid()
{
    qreal width = 800/gridWidth;
    qreal height = 600/gridHeight;
    BlockType basic = BlockType(0, QColor(255, 255, 255), "Void");
    clearBlockList();
    for(int i = 0; i < gridHeight; i++)
    {
        for(int z = 0; z < gridWidth; z++)
        {
            GraphicsBlock *block = new GraphicsBlock(z*width, i*height, width, height, basic);
            gBlockList[i][z] = block;
            scene->addItem(block);
        }
    }
}

void MainWidget::clearBlockList()
{
    for(int i = 0; i < 600; i++)
    {
        for(int z = 0; z < 800; z++)
        {
            gBlockList[i][z] = NULL;
        }
    }
}

void MainWidget::on_listWidget_itemSelectionChanged()
{
    Statics::currentBlockType = Statics::blockTypeList[ui->listWidget->currentIndex().row()];
}

void MainWidget::on_noClicPainting_stateChanged(int arg1)
{
    if(GraphicsBlock::painting)
        GraphicsBlock::painting = false;
    else
        GraphicsBlock::painting = true;
}
