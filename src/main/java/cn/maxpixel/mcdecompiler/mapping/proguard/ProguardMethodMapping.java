/*
 * MinecraftDecompiler. A tool/library to deobfuscate and decompile Minecraft.
 * Copyright (C) 2019-2020  MaxPixelStudios
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.maxpixel.mcdecompiler.mapping.proguard;

import cn.maxpixel.mcdecompiler.mapping.base.BaseMethodMapping;
import cn.maxpixel.mcdecompiler.mapping.components.Descriptor;
import cn.maxpixel.mcdecompiler.mapping.components.LineNumber;

public class ProguardMethodMapping extends BaseMethodMapping implements LineNumber, Descriptor.Mapped {
    private int[] lineNums = new int[2];
    private String mappedDescriptor;
    public ProguardMethodMapping(String unmappedName, String mappedName,
                                 String mappedDescriptor, int lineNumberS, int lineNumberE) {
        super(unmappedName, mappedName);
        this.mappedDescriptor = mappedDescriptor;
        this.lineNums[0] = lineNumberS;
        this.lineNums[1] = lineNumberE;
    }
    public ProguardMethodMapping() {}
    @Override
    public String getMappedDescriptor() {
        return mappedDescriptor;
    }
    @Override
    public void setMappedDescriptor(String mappedDescriptor) {
        this.mappedDescriptor = mappedDescriptor;
    }
    @Override
    public int getLineNumberS() {
        return lineNums[0];
    }
    @Override
    public int getLineNumberE() {
        return lineNums[1];
    }
    @Override
    public void setLineNumberS(int ns) {
        this.lineNums[0] = ns;
    }
    @Override
    public void setLineNumberE(int ne) {
        this.lineNums[1] = ne;
    }
}