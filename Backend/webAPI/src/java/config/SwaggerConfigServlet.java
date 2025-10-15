package config;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/openapi.json")
public class SwaggerConfigServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Cho phép CORS
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // === Build base URL động ===
        String scheme = req.getScheme(); // http hoặc https
        String serverName = req.getServerName(); // ví dụ: localhost hoặc 03dafbc27102.ngrok-free.app
        int port = req.getServerPort();
        String contextPath = req.getContextPath();

        String baseUrl = scheme + "://" + serverName
                + ((port == 80 || port == 443) ? "" : ":" + port)
                + contextPath;

        // Nếu chạy ngrok -> luôn force https
        if (serverName.contains("ngrok-free.app")) {
            baseUrl = "https://" + serverName + contextPath;
        }

        try (PrintWriter out = resp.getWriter()) {
            out.println("{");
            out.println("  \"openapi\": \"3.0.1\",");
            out.println("  \"info\": {");
            out.println("    \"title\": \"Battery Swap API\",");
            out.println("    \"description\": \"API cho hệ thống đổi pin xe điện\",");
            out.println("    \"version\": \"1.0.0\"");
            out.println("  },");
            out.println("  \"servers\": [");
            out.println("    { \"url\": \"" + baseUrl + "\", \"description\": \"dynamic server\" }");
            out.println("  ],");
            out.println("  \"paths\": {");

            // ==== API Login ====
            out.println("    \"/api/login\": {");
            out.println("      \"post\": {");
            out.println("        \"summary\": \"Đăng nhập hệ thống\",");
            out.println("        \"description\": \"Nhập email/password để đăng nhập\",");
            out.println("        \"requestBody\": {");
            out.println("          \"required\": true,");
            out.println("          \"content\": {");
            out.println("            \"application/json\": {");
            out.println("              \"schema\": {");
            out.println("                \"type\": \"object\",");
            out.println("                \"properties\": {");
            out.println("                  \"email\": { \"type\": \"string\" },");
            out.println("                  \"password\": { \"type\": \"string\" }");
            out.println("                },");
            out.println("                \"required\": [\"email\", \"password\"]");
            out.println("              },");
            out.println("              \"example\": {");
            out.println("                \"email\": \"nguyenvana@email.com\",");
            out.println("                \"password\": \"pass123\"");
            out.println("              }");
            out.println("            }");
            out.println("          }");
            out.println("        },");
            out.println("        \"responses\": {");
            out.println("          \"200\": { \"description\": \"Đăng nhập thành công\" },");
            out.println("          \"401\": { \"description\": \"Sai email hoặc password\" }");
            out.println("        }");
            out.println("      }");
            out.println("    },");

            // ==== API Register ====
            out.println("    \"/api/register\": {");
            out.println("      \"post\": {");
            out.println("        \"summary\": \"Đăng ký tài khoản mới\",");
            out.println("        \"description\": \"Tạo mới user với vai trò mặc định là Driver\",");
            out.println("        \"requestBody\": {");
            out.println("          \"required\": true,");
            out.println("          \"content\": {");
            out.println("            \"application/json\": {");
            out.println("              \"schema\": {");
            out.println("                \"type\": \"object\",");
            out.println("                \"properties\": {");
            out.println("                  \"fullName\": { \"type\": \"string\" },");
            out.println("                  \"phone\": { \"type\": \"string\" },");
            out.println("                  \"email\": { \"type\": \"string\" },");
            out.println("                  \"password\": { \"type\": \"string\" }");
            out.println("                },");
            out.println("                \"required\": [\"fullName\", \"phone\", \"email\", \"password\"]");
            out.println("              }");
            out.println("            }");
            out.println("          }");
            out.println("        },");
            out.println("        \"responses\": {");
            out.println("          \"201\": { \"description\": \"Đăng ký thành công\" },");
            out.println("          \"400\": { \"description\": \"Dữ liệu không hợp lệ\" },");
            out.println("          \"409\": { \"description\": \"Email đã tồn tại\" }");
            out.println("        }");
            out.println("      }");
            out.println("    },");

            // ==== API Get Packages ====
            out.println("    \"/api/getpackages\": {");
            out.println("      \"get\": {");
            out.println("        \"summary\": \"Lấy danh sách gói pin\",");
            out.println("        \"description\": \"Trả về toàn bộ danh sách các gói pin khả dụng\",");
            out.println("        \"responses\": {");
            out.println("          \"200\": { \"description\": \"Danh sách gói pin\" },");
            out.println("          \"204\": { \"description\": \"Không có gói pin nào\" }");
            out.println("        }");
            out.println("      }");
            out.println("    },");

            // ==== API Get Stations ====
            out.println("    \"/api/getstations\": {");
            out.println("      \"get\": {");
            out.println("        \"summary\": \"Lấy danh sách trạm đổi pin\",");
            out.println("        \"description\": \"Trả về toàn bộ danh sách các trạm khả dụng\",");
            out.println("        \"responses\": {");
            out.println("          \"200\": { \"description\": \"Danh sách trạm\" },");
            out.println("          \"204\": { \"description\": \"Không có trạm nào\" }");
            out.println("        }");
            out.println("      }");
            out.println("    },");

            // ==== API Add Package ====
            out.println("    \"/api/package\": {");
            out.println("      \"post\": {");
            out.println("        \"summary\": \"Thêm gói pin mới\",");
            out.println("        \"description\": \"Tạo mới một gói pin trong hệ thống\",");
            out.println("        \"requestBody\": {");
            out.println("          \"required\": true,");
            out.println("          \"content\": {");
            out.println("            \"application/json\": {");
            out.println("              \"schema\": {");
            out.println("                \"type\": \"object\",");
            out.println("                \"properties\": {");
            out.println("                  \"packageName\": { \"type\": \"string\", \"description\": \"Tên gói pin\" },");
            out.println("                  \"description\": { \"type\": \"string\", \"description\": \"Mô tả gói pin\" },");
            out.println("                  \"price\": { \"type\": \"number\", \"description\": \"Giá gói pin\" },");
            out.println("                  \"duration\": { \"type\": \"integer\", \"description\": \"Thời hạn gói (ngày)\" },");
            out.println("                  \"batteryType\": { \"type\": \"string\", \"description\": \"Loại pin\" },");
            out.println("                  \"capacity\": { \"type\": \"number\", \"description\": \"Dung lượng pin (kWh)\" }");
            out.println("                },");
            out.println("                \"required\": [\"packageName\", \"price\", \"duration\"]");
            out.println("              },");
            out.println("              \"example\": {");
            out.println("                \"packageName\": \"Gói Basic\",");
            out.println("                \"description\": \"Gói pin cơ bản cho xe máy điện\",");
            out.println("                \"price\": 200000,");
            out.println("                \"duration\": 30,");
            out.println("                \"batteryType\": \"Lithium-ion\",");
            out.println("                \"capacity\": 2.5");
            out.println("              }");
            out.println("            }");
            out.println("          }");
            out.println("        },");
            out.println("        \"responses\": {");
            out.println("          \"200\": {");
            out.println("            \"description\": \"Thêm gói pin thành công\",");
            out.println("            \"content\": {");
            out.println("              \"application/json\": {");
            out.println("                \"schema\": {");
            out.println("                  \"type\": \"object\",");
            out.println("                  \"properties\": {");
            out.println("                    \"status\": { \"type\": \"string\", \"example\": \"success\" },");
            out.println("                    \"package\": { \"type\": \"object\" }");
            out.println("                  }");
            out.println("                }");
            out.println("              }");
            out.println("            }");
            out.println("          },");
            out.println("          \"401\": { \"description\": \"Không có quyền thêm gói pin\" },");
            out.println("          \"500\": { \"description\": \"Lỗi server\" }");
            out.println("        }");
            out.println("      }");
            out.println("    },");

            // ==== API Package Revenue Statistics ====
            out.println("    \"/api/package-revenue-statistics\": {");
            out.println("      \"get\": {");
            out.println("        \"summary\": \"Thống kê doanh thu gói pin\",");
            out.println("        \"description\": \"Lấy thống kê doanh thu theo từng gói pin trong tháng hiện tại\",");
            out.println("        \"responses\": {");
            out.println("          \"200\": {");
            out.println("            \"description\": \"Lấy thống kê thành công\",");
            out.println("            \"content\": {");
            out.println("              \"application/json\": {");
            out.println("                \"schema\": {");
            out.println("                  \"type\": \"object\",");
            out.println("                  \"properties\": {");
            out.println("                    \"status\": { \"type\": \"string\", \"example\": \"success\" },");
            out.println("                    \"data\": {");
            out.println("                      \"type\": \"array\",");
            out.println("                      \"items\": {");
            out.println("                        \"type\": \"array\",");
            out.println("                        \"items\": {");
            out.println("                          \"oneOf\": [");
            out.println("                            { \"type\": \"integer\" },");
            out.println("                            { \"type\": \"string\" },");
            out.println("                            { \"type\": \"number\" }");
            out.println("                          ]");
            out.println("                        },");
            out.println("                        \"description\": \"[Package_ID, Name, SoLuongMua, TongDoanhThu]\"");
            out.println("                      }");
            out.println("                    }");
            out.println("                  }");
            out.println("                },");
            out.println("                \"example\": {");
            out.println("                  \"status\": \"success\",");
            out.println("                  \"data\": [");
            out.println("                    [1, \"Gói Basic\", 15, 3000000.0],");
            out.println("                    [2, \"Gói Premium\", 8, 4000000.0]");
            out.println("                  ]");
            out.println("                }");
            out.println("              }");
            out.println("            }");
            out.println("          },");
            out.println("          \"204\": { \"description\": \"Không có dữ liệu doanh thu\" },");
            out.println("          \"500\": { \"description\": \"Lỗi server\" }");
            out.println("        }");
            out.println("      }");
            out.println("    },");

            // ==== API Delete Package ====
            out.println("    \"/api/package/delete\": {");
            out.println("      \"post\": {");
            out.println("        \"summary\": \"Xóa gói pin (soft delete)\",");
            out.println("        \"description\": \"Đánh dấu gói pin là inactive thay vì xóa vật lý\",");
            out.println("        \"requestBody\": {");
            out.println("          \"required\": true,");
            out.println("          \"content\": {");
            out.println("            \"application/json\": {");
            out.println("              \"schema\": {");
            out.println("                \"type\": \"object\",");
            out.println("                \"properties\": {");
            out.println("                  \"packageId\": { \"type\": \"integer\", \"description\": \"ID của gói pin cần xóa\" }");
            out.println("                },");
            out.println("                \"required\": [\"packageId\"]");
            out.println("              },");
            out.println("              \"example\": {");
            out.println("                \"packageId\": 1");
            out.println("              }");
            out.println("            }");
            out.println("          }");
            out.println("        },");
            out.println("        \"responses\": {");
            out.println("          \"200\": {");
            out.println("            \"description\": \"Xóa gói pin thành công\",");
            out.println("            \"content\": {");
            out.println("              \"application/json\": {");
            out.println("                \"schema\": {");
            out.println("                  \"type\": \"object\",");
            out.println("                  \"properties\": {");
            out.println("                    \"status\": { \"type\": \"string\", \"example\": \"success\" },");
            out.println("                    \"message\": { \"type\": \"string\", \"example\": \"Package deleted successfully\" },");
            out.println("                    \"packageId\": { \"type\": \"integer\" }");
            out.println("                  }");
            out.println("                }");
            out.println("              }");
            out.println("            }");
            out.println("          },");
            out.println("          \"400\": { \"description\": \"ID gói pin không hợp lệ\" },");
            out.println("          \"404\": { \"description\": \"Không tìm thấy gói pin hoặc đã bị xóa\" },");
            out.println("          \"500\": { \"description\": \"Lỗi server\" }");
            out.println("        }");
            out.println("      },");
            out.println("      \"delete\": {");
            out.println("        \"summary\": \"Xóa gói pin (soft delete) - DELETE method\",");
            out.println("        \"description\": \"Đánh dấu gói pin là inactive thay vì xóa vật lý (sử dụng DELETE method)\",");
            out.println("        \"requestBody\": {");
            out.println("          \"required\": true,");
            out.println("          \"content\": {");
            out.println("            \"application/json\": {");
            out.println("              \"schema\": {");
            out.println("                \"type\": \"object\",");
            out.println("                \"properties\": {");
            out.println("                  \"packageId\": { \"type\": \"integer\", \"description\": \"ID của gói pin cần xóa\" }");
            out.println("                },");
            out.println("                \"required\": [\"packageId\"]");
            out.println("              },");
            out.println("              \"example\": {");
            out.println("                \"packageId\": 1");
            out.println("              }");
            out.println("            }");
            out.println("          }");
            out.println("        },");
            out.println("        \"responses\": {");
            out.println("          \"200\": {");
            out.println("            \"description\": \"Xóa gói pin thành công\",");
            out.println("            \"content\": {");
            out.println("              \"application/json\": {");
            out.println("                \"schema\": {");
            out.println("                  \"type\": \"object\",");
            out.println("                  \"properties\": {");
            out.println("                    \"status\": { \"type\": \"string\", \"example\": \"success\" },");
            out.println("                    \"message\": { \"type\": \"string\", \"example\": \"Package deleted successfully\" },");
            out.println("                    \"packageId\": { \"type\": \"integer\" }");
            out.println("                  }");
            out.println("                }");
            out.println("              }");
            out.println("            }");
            out.println("          },");
            out.println("          \"400\": { \"description\": \"ID gói pin không hợp lệ\" },");
            out.println("          \"404\": { \"description\": \"Không tìm thấy gói pin hoặc đã bị xóa\" },");
            out.println("          \"500\": { \"description\": \"Lỗi server\" }");
            out.println("        }");
            out.println("      }");
            out.println("    }");

            out.println("  }");
            out.println("}");
        }
    }
}
