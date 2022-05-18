package com.fms.pms.domain.commands;

import com.fms.pms.models.Errors;
import com.fms.pms.models.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseCommand {

  private Header header;

  private Errors errors;
}
