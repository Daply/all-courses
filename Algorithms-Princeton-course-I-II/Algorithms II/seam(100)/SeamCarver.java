import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private static final double MAX_ENERGY = 1000.0;

    private int[][] pictureRGB;
    private int pictureWidth;
    private int pictureHeight;
    private double[][] energies;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        initPicture(picture);
    }

    private void initPicture(Picture picture) {
        this.pictureWidth = picture.width();
        this.pictureHeight = picture.height();
        this.pictureRGB = new int[this.pictureHeight][this.pictureWidth];
        constructPictureRGBTable(picture);
        this.energies = new double[this.pictureHeight][this.pictureWidth];
        constructEnergiesArray();
    }

    private void constructPictureRGBTable(Picture picture) {
        for (int x = 0; x < this.pictureWidth; x++) {
            for (int y = 0; y < this.pictureHeight; y++) {
                this.pictureRGB[y][x] = picture.getRGB(x, y);
            }
        }
    }

    private void constructEnergiesArray() {
        for (int x = 0; x < this.pictureWidth; x++) {
            for (int y = 0; y < this.pictureHeight; y++) {
                 this.energies[y][x] = energy(x, y);
            }
        }
    }

    // current picture
    public Picture picture() {
        return createPictureFromRGBTable();
    }

    private Picture createPictureFromRGBTable() {
        Picture picture = new Picture(this.pictureWidth, this.pictureHeight);
        for (int x = 0; x < this.pictureWidth; x++) {
            for (int y = 0; y < this.pictureHeight; y++) {
                picture.setRGB(x, y, this.pictureRGB[y][x]);
            }
        }
        return picture;
    }

    // width of current picture
    public int width() {
        return this.pictureWidth;
    }

    // height of current picture
    public int height() {
        return this.pictureHeight;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validateX(x);
        validateY(y);

        if ((x == 0 || x == this.pictureWidth - 1) ||
                (y == 0 || y == this.pictureHeight - 1)) {
            return MAX_ENERGY;
        }

        return Math.sqrt(deltaX(x, y) + deltaY(x, y));
    }

    private double deltaX(int x, int y) {
        int r1 = 0;
        int r2 = 0;
        int g1 = 0;
        int g2 = 0;
        int b1 = 0;
        int b2 = 0;
        if (x > 0) {
            int color = this.pictureRGB[y][x - 1];
            r1 = (color >> 16) & 0xFF;
            g1 = (color >>  8) & 0xFF;
            b1 = (color >>  0) & 0xFF;
        }
        if (x < this.pictureWidth - 1) {
            int color = this.pictureRGB[y][x + 1];
            r2 = (color >> 16) & 0xFF;
            g2 = (color >>  8) & 0xFF;
            b2 = (color >>  0) & 0xFF;
        }
        return delta(r1, g1, b1, r2, g2, b2);
    }

    private double deltaY(int x, int y) {
        int r1 = 0;
        int r2 = 0;
        int g1 = 0;
        int g2 = 0;
        int b1 = 0;
        int b2 = 0;
        if (y > 0) {
            int color = this.pictureRGB[y - 1][x];
            r1 = (color >> 16) & 0xFF;
            g1 = (color >>  8) & 0xFF;
            b1 = (color >>  0) & 0xFF;
        }
        if (y < this.pictureHeight - 1) {
            int color = this.pictureRGB[y + 1][x];
            r2 = (color >> 16) & 0xFF;
            g2 = (color >>  8) & 0xFF;
            b2 = (color >>  0) & 0xFF;
        }
        return delta(r1, g1, b1, r2, g2, b2);
    }

    private double delta(int red1, int green1, int blue1, int red2, int green2, int blue2) {
        int rx = Math.abs(red1 - red2);
        int gx = Math.abs(green1 - green2);
        int bx = Math.abs(blue1 - blue2);
        return Math.pow(rx, 2) + Math.pow(gx, 2) + Math.pow(bx, 2);
    }

    // sequence of indices for horizontal seam
    public int[] findVerticalSeam() {
        int[] seam = new int[this.pictureHeight];
        SeamNode[][] seamNodes = new SeamNode[this.pictureHeight][this.pictureWidth];
        SeamNode minimum = null;
        double minimumNextEnergy = Double.MAX_VALUE;

        for (int x = 0; x < this.pictureWidth; x++) {
            SeamNode seamNodeRoot = new SeamNode();
            seamNodeRoot.value = this.energies[0][x];
            seamNodes[0][x] = seamNodeRoot;
        }

        for (int y = 1; y < this.pictureHeight; y++) {
            for (int x = 0; x < this.pictureWidth; x++) {
                if (x > 0) {
                    SeamNode seamNodeLeft = new SeamNode();
                    seamNodeLeft.valueOfPrevIndex = x;
                    seamNodeLeft.valueOfIndex = x - 1;
                    seamNodeLeft.value = seamNodes[y - 1][x].value + this.energies[y][x - 1];
                    if (seamNodes[y][x - 1] == null) {
                        seamNodes[y][x - 1] = seamNodeLeft;
                    } else {
                        SeamNode existingSeamNode = seamNodes[y][x - 1];
                        if (existingSeamNode.value > seamNodeLeft.value) {
                            seamNodes[y][x - 1] = seamNodeLeft;
                        }
                    }
                }

                SeamNode seamNodeMiddle = new SeamNode();
                seamNodeMiddle.valueOfPrevIndex = x;
                seamNodeMiddle.valueOfIndex = x;
                seamNodeMiddle.value = seamNodes[y - 1][x].value + this.energies[y][x];
                if (seamNodes[y][x] == null) {
                    seamNodes[y][x] = seamNodeMiddle;
                }
                else {
                    SeamNode existingSeamNode = seamNodes[y][x];
                    if (existingSeamNode.value > seamNodeMiddle.value) {
                        seamNodes[y][x] = seamNodeMiddle;
                    }
                }

                if (x < this.pictureWidth - 1) {
                    SeamNode seamNodeRight = new SeamNode();
                    seamNodeRight.valueOfPrevIndex = x;
                    seamNodeRight.valueOfIndex = x + 1;
                    seamNodeRight.value = seamNodes[y - 1][x].value + this.energies[y][x + 1];
                    if (seamNodes[y][x + 1] == null) {
                        seamNodes[y][x + 1] = seamNodeRight;
                    } else {
                        SeamNode existingSeamNode = seamNodes[y][x + 1];
                        if (existingSeamNode.value > seamNodeRight.value) {
                            seamNodes[y][x + 1] = seamNodeRight;
                        }
                    }
                }
            }
        }

        for (int xLast = 0; xLast < this.pictureWidth; xLast++) {
            if (seamNodes[this.pictureHeight - 1][xLast] != null) {
                if (seamNodes[this.pictureHeight - 1][xLast].value < minimumNextEnergy) {
                    minimumNextEnergy = seamNodes[this.pictureHeight - 1][xLast].value;
                    minimum = seamNodes[this.pictureHeight - 1][xLast];
                }
            }
        }
        SeamNode node = minimum;
        if (node != null) {
            seam[this.pictureHeight - 1] = node.valueOfIndex;
            for (int y = this.pictureHeight - 2; y >= 0; y--) {
                int prevX = node.valueOfPrevIndex;
                seam[y] = prevX;
                node = seamNodes[y][prevX];
            }
        }
        return seam;
    }

    public int[] findHorizontalSeam() {
        int[] seam = new int[this.pictureWidth];
        SeamNode[][] seamNodes = new SeamNode[this.pictureWidth][this.pictureHeight];
        SeamNode minimum = null;
        double minimumNextEnergy = Double.MAX_VALUE;

        for (int y = 0; y < this.pictureHeight; y++) {
            SeamNode seamNodeRoot = new SeamNode();
            seamNodeRoot.value = this.energies[y][0];
            seamNodes[0][y] = seamNodeRoot;
        }

        for (int x = 1; x < this.pictureWidth; x++) {
            for (int y = 0; y < this.pictureHeight; y++) {
                if (y > 0) {
                    SeamNode seamNodeLeft = new SeamNode();
                    seamNodeLeft.valueOfPrevIndex = y;
                    seamNodeLeft.valueOfIndex = y - 1;
                    seamNodeLeft.value = seamNodes[x - 1][y].value + this.energies[y - 1][x];
                    if (seamNodes[x][y - 1] == null) {
                        seamNodes[x][y - 1] = seamNodeLeft;
                    } else {
                        SeamNode existingSeamNode = seamNodes[x][y - 1];
                        if (existingSeamNode.value > seamNodeLeft.value) {
                            seamNodes[x][y - 1] = seamNodeLeft;
                        }
                    }
                }

                SeamNode seamNodeMiddle = new SeamNode();
                seamNodeMiddle.valueOfPrevIndex = y;
                seamNodeMiddle.valueOfIndex = y;
                seamNodeMiddle.value = seamNodes[x - 1][y].value + this.energies[y][x];
                if (seamNodes[x][y] == null) {
                    seamNodes[x][y] = seamNodeMiddle;
                }
                else {
                    SeamNode existingSeamNode = seamNodes[x][y];
                    if (existingSeamNode.value > seamNodeMiddle.value) {
                        seamNodes[x][y] = seamNodeMiddle;
                    }
                }

                if (y < this.pictureHeight - 1) {
                    SeamNode seamNodeRight = new SeamNode();
                    seamNodeRight.valueOfPrevIndex = y;
                    seamNodeRight.valueOfIndex = y + 1;
                    seamNodeRight.value = seamNodes[x - 1][y].value + this.energies[y + 1][x];
                    if (seamNodes[x][y + 1] == null) {
                        seamNodes[x][y + 1] = seamNodeRight;
                    } else {
                        SeamNode existingSeamNode = seamNodes[x][y + 1];
                        if (existingSeamNode.value > seamNodeRight.value) {
                            seamNodes[x][y + 1] = seamNodeRight;
                        }
                    }
                }
            }
        }

        for (int yLast = 0; yLast < this.pictureHeight; yLast++) {
            if (seamNodes[this.pictureWidth - 1][yLast] != null) {
                if (seamNodes[this.pictureWidth - 1][yLast].value < minimumNextEnergy) {
                    minimumNextEnergy = seamNodes[this.pictureWidth - 1][yLast].value;
                    minimum = seamNodes[this.pictureWidth - 1][yLast];
                }
            }
        }
        SeamNode node = minimum;
        if (node != null) {
            seam[this.pictureWidth - 1] = node.valueOfIndex;
            for (int x = this.pictureWidth - 2; x >= 0; x--) {
                int prevY = node.valueOfPrevIndex;
                seam[x] = prevY;
                node = seamNodes[x][prevY];
            }
        }
        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }

        validateHorizontalSeam(seam);

        if (this.pictureHeight <= 1) {
            throw new IllegalArgumentException();
        }

        shiftRGBsAndEnergiesHorizontal(seam);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException();
        }

        validateVerticalSeam(seam);

        if (this.pictureWidth <= 1) {
            throw new IllegalArgumentException();
        }

        shiftRGBsAndEnergiesVertical(seam);
    }

    private void shiftRGBsAndEnergiesHorizontal(int[] seam) {
        int [][] newPictureRGB = new int[this.pictureHeight - 1][this.pictureWidth];
        double [][] newEnergies = new double[this.pictureHeight - 1][this.pictureWidth];
        for (int x = 0; x < this.pictureWidth; x++) {
            for (int y = 0; y < seam[x]; y++) {
                newPictureRGB[y][x] = this.pictureRGB[y][x];
                newEnergies[y][x] = this.energies[y][x];
            }
            for (int y = seam[x] + 1; y < this.pictureHeight; y++) {
                newPictureRGB[y - 1][x] = this.pictureRGB[y][x];
                newEnergies[y - 1][x] = this.energies[y][x];
            }
        }

        this.pictureRGB = newPictureRGB;
        this.energies = newEnergies;
        this.pictureHeight--;
        constructEnergiesArray();
    }

    private void shiftRGBsAndEnergiesVertical(int[] seam) {
        int [][] newPictureRGB = new int[this.pictureHeight][this.pictureWidth - 1];
        double [][] newEnergies = new double[this.pictureHeight][this.pictureWidth - 1];
        for (int y = 0; y < this.pictureHeight; y++) {
            System.arraycopy(this.pictureRGB[y], 0, newPictureRGB[y], 0, seam[y]);
            System.arraycopy(this.pictureRGB[y], seam[y] + 1, newPictureRGB[y], seam[y], (this.energies[y].length - seam[y] - 1));

            System.arraycopy(this.energies[y], 0, newEnergies[y], 0, seam[y]);
            System.arraycopy(this.energies[y], seam[y] + 1, newEnergies[y], seam[y], (this.energies[y].length - seam[y] - 1));
        }

        this.pictureRGB = newPictureRGB;
        this.energies = newEnergies;
        this.pictureWidth--;
        constructEnergiesArray();
    }

    private class SeamNode {
        int valueOfPrevIndex;
        int valueOfIndex;
        double value;
    }

    private void validateX(int x) {
        if (x < 0 || x >= this.pictureWidth) {
            throw new IllegalArgumentException();
        }
    }

    private void validateY(int y) {
        if (y < 0 || y >= this.pictureHeight) {
            throw new IllegalArgumentException();
        }
    }

    private void validateHorizontalSeam(int[] seam) {
        if (seam.length < this.pictureWidth || seam.length > this.pictureWidth) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= this.pictureHeight) {
                throw new IllegalArgumentException();
            }
            if (i > 0) {
                if (Math.abs(seam[i] - seam[i-1]) > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private void validateVerticalSeam(int[] seam) {
        if (seam.length < this.pictureHeight || seam.length > this.pictureHeight) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= this.pictureWidth) {
                throw new IllegalArgumentException();
            }
            if (i > 0) {
                if (Math.abs(seam[i] - seam[i-1]) > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    //  unit testing (optional)
    public static void main(String[] args) {
    }

}
