import mongoose, { Schema } from 'mongoose'

const medicamentoSchema = new Schema({
  nombre: {
    type: String
  },
  cantidad: {
    type: String
  },
  gramos: {
    type: String
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

medicamentoSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      nombre: this.nombre,
      cantidad: this.cantidad,
      gramos: this.gramos,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Medicamento', medicamentoSchema)

export const schema = model.schema
export default model
