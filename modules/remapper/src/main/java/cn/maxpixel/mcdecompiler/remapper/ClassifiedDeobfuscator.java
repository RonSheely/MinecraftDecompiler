/*
 * MinecraftDecompiler. A tool/library to deobfuscate and decompile jars.
 * Copyright (C) 2019-2024 MaxPixelStudios(XiaoPangxie732)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.maxpixel.mcdecompiler.remapper;

import cn.maxpixel.mcdecompiler.common.app.SideType;
import cn.maxpixel.mcdecompiler.common.app.util.DownloadingUtil;
import cn.maxpixel.mcdecompiler.common.util.Utils;
import cn.maxpixel.mcdecompiler.mapping.NamespacedMapping;
import cn.maxpixel.mcdecompiler.mapping.PairedMapping;
import cn.maxpixel.mcdecompiler.mapping.collection.ClassifiedMapping;
import cn.maxpixel.mcdecompiler.mapping.format.MappingFormats;
import cn.maxpixel.mcdecompiler.mapping.remapper.ClassifiedMappingRemapper;
import cn.maxpixel.mcdecompiler.mapping.trait.NamespacedTrait;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;

public class ClassifiedDeobfuscator extends Deobfuscator<ClassifiedMappingRemapper> {
    public ClassifiedDeobfuscator(String version, SideType side) {
        this(version, side, DeobfuscationOptions.DEFAULT);
    }

    public ClassifiedDeobfuscator(String version, SideType side, DeobfuscationOptions options) {
        this(MappingFormats.PROGUARD.read(DownloadingUtil.downloadMappingSync(version, side)), options);
    }

    public ClassifiedDeobfuscator(ClassifiedMapping<PairedMapping> mappings) {
        this(mappings, DeobfuscationOptions.DEFAULT);
    }

    public ClassifiedDeobfuscator(ClassifiedMapping<PairedMapping> mappings, DeobfuscationOptions options) {
        super(options);
        this.remapper = new ClassifiedMappingRemapper(mappings, options.reverse);
    }

    public ClassifiedDeobfuscator(ClassifiedMapping<NamespacedMapping> mappings, String targetNamespace) {
        this(mappings, targetNamespace, DeobfuscationOptions.DEFAULT);
    }

    public ClassifiedDeobfuscator(ClassifiedMapping<NamespacedMapping> mappings, String targetNamespace, DeobfuscationOptions options) {
        super(options);
        this.remapper = new ClassifiedMappingRemapper(mappings, inferTargetNamespace(targetNamespace, mappings), options.reverse);
    }

    private static String inferTargetNamespace(String targetNamespace, @NotNull ClassifiedMapping<NamespacedMapping> mappings) {
        if (Utils.isStringNotBlank(targetNamespace)) return targetNamespace;
        var namespaces = mappings.getTrait(NamespacedTrait.class).namespaces;
        if (namespaces.size() > 2) throw new IllegalArgumentException("Cannot infer a target namespace. You must manually specify a target namespace.");
        return namespaces.last();
    }

    @Override
    public ClassifiedDeobfuscator deobfuscate(Path source, Path target) throws IOException {
        super.deobfuscate(source, target);
        return this;
    }
}