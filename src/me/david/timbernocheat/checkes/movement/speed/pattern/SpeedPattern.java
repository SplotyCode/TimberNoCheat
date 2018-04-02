package me.david.timbernocheat.checkes.movement.speed.pattern;

public class SpeedPattern {

    private final String name;
    private final int speed;
    private final int jumpboost;
    private final int slowness;
    private float verticaldown, verticalup, horizontal;
    private final boolean vehicle;
    private final boolean liquid;
    private final boolean ice;
    private final boolean block;
    private final boolean sprint;
    private final boolean sneak;
    private final boolean web;
    private final boolean ladder;
    private final boolean slabs;
    private final boolean stairs;
    private final boolean blockover;
    private final boolean wasonground;
    private final boolean soulsand;

    public SpeedPattern(String name, int speed,
                        int jumpboost, int slowness,
                        float verticaldown, float verticalup, float horizontal,
                        boolean vehicle, boolean liquid, boolean ice, boolean block,
                        boolean sprint, boolean sneak, boolean web, boolean ladder,
                        boolean slabs, boolean stairs, boolean blockover, boolean wasonground,
                        boolean soulsand) {
        this.name = name;
        this.speed = speed;
        this.jumpboost = jumpboost;
        this.slowness = slowness;
        this.verticaldown = verticaldown;
        this.verticalup = verticalup;
        this.horizontal = horizontal;
        this.vehicle = vehicle;
        this.liquid = liquid;
        this.ice = ice;
        this.block = block;
        this.sprint = sprint;
        this.sneak = sneak;
        this.web = web;
        this.ladder = ladder;
        this.slabs = slabs;
        this.stairs = stairs;
        this.blockover = blockover;
        this.wasonground = wasonground;
        this.soulsand = soulsand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpeedPattern that = (SpeedPattern) o;

        if (!name.equals(that.name)) return false;
        if (speed != that.speed) return false;
        if (jumpboost != that.jumpboost) return false;
        if (slowness != that.slowness) return false;
        if (Float.compare(that.verticaldown, verticaldown) != 0) return false;
        if (Float.compare(that.verticalup, verticalup) != 0) return false;
        if (Float.compare(that.horizontal, horizontal) != 0) return false;
        if (vehicle != that.vehicle) return false;
        if (liquid != that.liquid) return false;
        if (ice != that.ice) return false;
        if (block != that.block) return false;
        if (sprint != that.sprint) return false;
        if (sneak != that.sneak) return false;
        if (web != that.web) return false;
        if (ladder != that.ladder) return false;
        if (slabs != that.slabs) return false;
        if (stairs != that.stairs) return false;
        if (blockover != that.blockover) return false;
        if (wasonground != that.wasonground) return false;
        return soulsand == that.soulsand;
    }

    public boolean equalsnospeed(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpeedPattern that = (SpeedPattern) o;

        if (speed != that.speed) return false;
        if (jumpboost != that.jumpboost) return false;
        if (slowness != that.slowness) return false;
        if (vehicle != that.vehicle) return false;
        if (liquid != that.liquid) return false;
        if (ice != that.ice) return false;
        if (block != that.block) return false;
        if (sprint != that.sprint) return false;
        if (sneak != that.sneak) return false;
        if (web != that.web) return false;
        if (ladder != that.ladder) return false;
        if (slabs != that.slabs) return false;
        if (stairs != that.stairs) return false;
        if (blockover != that.blockover) return false;
        if (wasonground != that.wasonground) return false;
        return soulsand == that.soulsand;
    }

    @Override
    public int hashCode() {
        int result = speed;
        result = 31 * result + jumpboost;
        result = 31 * result + slowness;
        result = 31 * result + (verticalup != +0.0f ? Float.floatToIntBits(verticalup) : 0);
        result = 31 * result + (verticaldown != +0.0f ? Float.floatToIntBits(verticaldown) : 0);
        result = 31 * result + (horizontal != +0.0f ? Float.floatToIntBits(horizontal) : 0);
        result = 31 * result + (vehicle ? 1 : 0);
        result = 31 * result + (liquid ? 1 : 0);
        result = 31 * result + (ice ? 1 : 0);
        result = 31 * result + (block ? 1 : 0);
        result = 31 * result + (sprint ? 1 : 0);
        result = 31 * result + (sneak ? 1 : 0);
        result = 31 * result + (web ? 1 : 0);
        result = 31 * result + (ladder ? 1 : 0);
        result = 31 * result + (slabs ? 1 : 0);
        result = 31 * result + (stairs ? 1 : 0);
        result = 31 * result + (blockover ? 1 : 0);
        result = 31 * result + (wasonground ? 1 : 0);
        result = 31 * result + (soulsand ? 1 : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getJumpboost() {
        return jumpboost;
    }

    public int getSlowness() {
        return slowness;
    }

    public float getVerticaldown() {
        return verticaldown;
    }

    public void setVerticaldown(float verticaldown) {
        this.verticaldown = verticaldown;
    }

    public float getVerticalup() {
        return verticalup;
    }

    public void setVerticalup(float verticalup) {
        this.verticalup = verticalup;
    }

    public float getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(float horizontal) {
        this.horizontal = horizontal;
    }

    public boolean isVehicle() {
        return vehicle;
    }

    public boolean isLiquid() {
        return liquid;
    }

    public boolean isIce() {
        return ice;
    }

    public boolean isBlock() {
        return block;
    }

    public boolean isSprint() {
        return sprint;
    }

    public boolean isSneak() {
        return sneak;
    }

    public boolean isWeb() {
        return web;
    }

    public boolean isLadder() {
        return ladder;
    }

    public boolean isSlabs() {
        return slabs;
    }

    public boolean isStairs() {
        return stairs;
    }

    public boolean isBlockover() {
        return blockover;
    }

    public boolean isWasonground() {
        return wasonground;
    }

    public boolean isSoulsand() {
        return soulsand;
    }
}
